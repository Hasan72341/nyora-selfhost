# syntax=docker/dockerfile:1.7

# ============================================================================
# Nyora — fully self-hosted, single-image build.
#
# Runs the web SPA + the parser HELPER (the kotatsu-parsers JVM engine) all
# LOCALLY, so nothing depends on the hosted Nyora cloud. The only optional
# remote piece is library sync (NYORA_SYNC_URL) — leave it unset / self-host it
# and the app is 100% local.
#
# Topology inside the final container (all same-origin, no CORS):
#   Caddy :8080  ── serves /srv (the built SPA)
#                └─ handle_path /api/*  → reverse_proxy 127.0.0.1:8788 (helper)
#   Java helper :8788 (loopback only)  → talks to FlareSolverr over the network
#
# Everything is built FROM SOURCE at image-build time — no prebuilt artifacts:
#   1. helper — clone nyora-linux (+ nyora-shared submodule), build the fat jar
#   2. web    — clone nyora-web, npm ci + npm run build → dist  (esbuild)
#   3. runtime— eclipse-temurin:17-jre + caddy; copies jar + dist; non-root
# ============================================================================

# ----------------------------------------------------------------------------
# Stage 1: build the parser helper fat jar from source (kotatsu-parsers engine
# + NyoraRestServer). Needs the FULL JDK + Kotlin/Gradle toolchain.
# ----------------------------------------------------------------------------
FROM eclipse-temurin:17-jdk AS helper

ARG NYORA_ENGINE_REF=main
ARG NYORA_ENGINE_REPO=https://github.com/Hasan72341/nyora-linux.git

RUN apt-get update \
    && apt-get install -y --no-install-recommends git ca-certificates \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /build

# Clone the helper build repo AND its nyora-shared submodule (provides
# commonMain/jvmMain + the SQLDelight schema — both required to build).
RUN git clone --depth 1 --recurse-submodules --shallow-submodules \
      --branch "${NYORA_ENGINE_REF}" "${NYORA_ENGINE_REPO}" nyora-linux

WORKDIR /build/nyora-linux

# Build ONLY :shared:helperJar — never the desktop app, so the Compose Desktop /
# Skiko / Chromium artifacts are never fetched. Needs outbound network for
# Gradle + the jitpack kotatsu-parsers engine (do NOT use --offline).
RUN --mount=type=cache,target=/root/.gradle \
    chmod +x ./gradlew \
    && ./gradlew :shared:helperJar --no-daemon --console=plain \
    && cp shared/build/libs/nyora-helper.jar /nyora-helper.jar \
    && test -s /nyora-helper.jar

# ----------------------------------------------------------------------------
# Stage 2: build the static SPA (dist/) from the nyora-web repo with esbuild.
# ----------------------------------------------------------------------------
FROM node:22-alpine AS web

ARG NYORA_WEB_REF=main
ARG NYORA_WEB_REPO=https://github.com/Hasan72341/nyora-web.git

RUN apk add --no-cache git
WORKDIR /app

# Clone the web SPA source and build it.
RUN git clone --depth 1 --branch "${NYORA_WEB_REF}" "${NYORA_WEB_REPO}" .
RUN npm ci --no-audit --no-fund || npm install --no-audit --no-fund
# node build.mjs: rm -rf dist, cp web/→dist/ (incl env.js/sw.js), esbuild bundle.
RUN npm run build && test -f dist/index.html && test -f dist/env.js

# ----------------------------------------------------------------------------
# Stage 3: lean runtime — JRE + Caddy. No JDK, no build toolchain.
# ----------------------------------------------------------------------------
FROM eclipse-temurin:17-jre AS runtime

RUN set -eux; \
    apt-get update; \
    apt-get install -y --no-install-recommends \
        curl ca-certificates tini debian-keyring debian-archive-keyring apt-transport-https gnupg; \
    curl -1sLf 'https://dl.cloudsmith.io/public/caddy/stable/gpg.key' \
        | gpg --dearmor -o /usr/share/keyrings/caddy-stable-archive-keyring.gpg; \
    curl -1sLf 'https://dl.cloudsmith.io/public/caddy/stable/debian.deb.txt' \
        | tee /etc/apt/sources.list.d/caddy-stable.list; \
    apt-get update; \
    apt-get install -y --no-install-recommends caddy; \
    apt-get purge -y --auto-remove gnupg apt-transport-https; \
    rm -rf /var/lib/apt/lists/*

# Non-root runtime user + writable dirs (/srv SPA, /data helper DB, /config Caddy).
RUN groupadd --system --gid 10001 nyora \
    && useradd --system --uid 10001 --gid nyora --home-dir /home/nyora --create-home nyora \
    && mkdir -p /srv /data /config /var/log/caddy \
    && chown -R nyora:nyora /srv /data /config /var/log/caddy

COPY --from=helper /nyora-helper.jar /opt/nyora/nyora-helper.jar
COPY --from=web    /app/dist         /srv
COPY Caddyfile            /etc/caddy/Caddyfile
COPY docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh
RUN chmod +x /usr/local/bin/docker-entrypoint.sh \
    && chown -R nyora:nyora /srv /opt/nyora

# Runtime config — all overridable at compose/run time.
# Sync stays REMOTE by default; the helper is loopback-only behind /api.
ENV NYORA_SYNC_URL=https://stream.hasanraza.tech \
    FLARESOLVERR_URL=http://flaresolverr:8191/v1 \
    NYORA_HELPER_PORT=8788 \
    NYORA_HELPER_JAR=/opt/nyora/nyora-helper.jar \
    SRV_ROOT=/srv \
    XDG_DATA_HOME=/data \
    XDG_CONFIG_HOME=/data \
    XDG_CACHE_HOME=/data \
    HOME=/home/nyora \
    JAVA_OPTS="-Xmx320m -Xss512k -XX:MaxMetaspaceSize=128m -XX:ReservedCodeCacheSize=64m -XX:+UseSerialGC"

USER nyora
WORKDIR /opt/nyora

EXPOSE 8080
VOLUME ["/data"]

# Healthcheck goes through Caddy's /api strip → helper /health (proves both).
HEALTHCHECK --interval=30s --timeout=5s --start-period=90s --retries=5 \
    CMD curl -fsS http://127.0.0.1:8080/api/health || exit 1

ENTRYPOINT ["/usr/bin/tini", "--", "/usr/local/bin/docker-entrypoint.sh"]
