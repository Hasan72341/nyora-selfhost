<div align="center">

<img src="https://nyora.pages.dev/icon.png" width="110" alt="Nyora" />

# Nyora — Self-Hosted Manga Reader in Docker

### Run your own manga / manhwa / manhua reader — one container, fully local.

**`hasan72341/nyora`** is a one-command, **self-hosted Docker** deployment of
[Nyora](https://nyora.pages.dev): the **web reader UI** and the **parser helper** (the
[Kotatsu](https://github.com/KotatsuApp/kotatsu-parsers) engine) bundled in a single container,
so every source is parsed **on your own machine**. No cloud, no account, no tracking — a
self-hosted, ad-free **Tachiyomi / Mihon / Kotatsu alternative** you own end to end.

<p>
  <a href="https://hub.docker.com/r/hasan72341/nyora"><img alt="Docker Hub" src="https://img.shields.io/badge/Docker_Hub-hasan72341%2Fnyora-2496ED?style=for-the-badge&logo=docker&logoColor=white" /></a>
  <a href="https://hub.docker.com/r/hasan72341/nyora"><img alt="Docker Pulls" src="https://img.shields.io/docker/pulls/hasan72341/nyora?style=for-the-badge&logo=docker&logoColor=white" /></a>
  <img alt="Self-hosted" src="https://img.shields.io/badge/Self--hosted-0AE448?style=for-the-badge&logo=serverless&logoColor=black" />
  <a href="./LICENSE"><img alt="License: Apache 2.0" src="https://img.shields.io/badge/License-Apache_2.0-blue.svg?style=for-the-badge" /></a>
</p>

</div>

> **In one line:** `docker run -d -p 8080:8080 -v nyora-data:/data hasan72341/nyora` → open
> `http://localhost:8080` and you're reading, with every source parsed locally.

<div align="center">

<img src="https://raw.githubusercontent.com/Nyora-Manga/nyora-web/main/docs/screenshots/desktop/discover-dark.png" width="49%" alt="Discover — trending manga" /> <img src="https://raw.githubusercontent.com/Nyora-Manga/nyora-web/main/docs/screenshots/desktop/explore-dark.png" width="49%" alt="Library" />
<img src="https://raw.githubusercontent.com/Nyora-Manga/nyora-web/main/docs/screenshots/desktop/settings-dark.png" width="49%" alt="Settings" /> <img src="https://raw.githubusercontent.com/Nyora-Manga/nyora-web/main/docs/screenshots/desktop/welcome-dark.png" width="49%" alt="Welcome" />

<sub>The Nyora web reader — running entirely from your own Docker container.</sub>

</div>

---

## Table of contents

- [Why self-host Nyora](#why-self-host-nyora)
- [Quick start](#quick-start)
- [How it works (architecture)](#how-it-works)
- [Is it really fully local?](#is-it-really-fully-local)
- [Configuration](#configuration)
- [Behind a reverse proxy / HTTPS](#behind-a-reverse-proxy--https)
- [Updating](#updating)
- [Build from source](#build-from-source)
- [Troubleshooting](#troubleshooting)
- [FAQ](#faq)
- [Requirements & notes](#requirements--notes)
- [Related](#related)

---

## Why self-host Nyora

| | |
|---|---|
| 🐳 **One command** | `docker run …` or `docker compose up` — the web UI **and** the parser engine come up together, no wiring. |
| 🔒 **Fully local** | Every source is fetched and parsed by the **in-container helper** — nothing routes through Nyora's servers to read. |
| 📚 **Hundreds of sources** | The [Kotatsu](https://github.com/KotatsuApp/kotatsu-parsers) parser engine — manga, manhwa & manhua — with covers/pages proxied so images just load. |
| 🚫 **No ads, no tracking, no account** | Open-source (Apache-2.0). Reading needs no sign-in; optional library **sync** is the only remote piece, and you can self-host or skip it. |
| 🧩 **The whole reader, on your box** | A polished PWA reader (standard + webtoon, LTR/RTL/vertical), library, categories, history — served from your own host. |
| 🌐 **Reach it anywhere** | localhost, a LAN IP, or behind your own HTTPS reverse proxy — the app auto-detects its origin, so there's nothing to reconfigure. |

## Quick start

### Option A — run the published image (fastest)

```sh
docker run -d --name nyora -p 8080:8080 -v nyora-data:/data hasan72341/nyora:latest
```
Open **http://localhost:8080**. That's it.

### Option B — with Compose (adds the Cloudflare-challenge solver)

Some sources sit behind a browser challenge; the [FlareSolverr](https://github.com/FlareSolverr/FlareSolverr)
sidecar handles those. Grab the compose file and pull the image:

```sh
git clone https://github.com/Nyora-Manga/nyora-docker.git
cd nyora-docker
docker compose pull && docker compose up -d
```

### Option C — build from source

```sh
git clone https://github.com/Nyora-Manga/nyora-docker.git
cd nyora-docker
docker compose up --build          # compiles the helper + web bundle from source
```

Then open **http://localhost:8080** in any browser.

## How it works

Everything is **same-origin** inside one container — no CORS, no cloud, nothing to configure.

```
  ┌──────────────────────── nyora container (:8080) ────────────────────────┐
  │                                                                          │
  │   Caddy :8080                                                            │
  │     ├─ /*      → the web reader SPA (static files)                       │
  │     └─ /api/*  → 127.0.0.1:8788   (prefix stripped)                      │
  │                       │                                                  │
  │                 Java parser helper  (Kotatsu engine + NyoraRestServer)   │
  │                 — parses every source locally, proxies covers/pages,     │
  │                 handles hotlink referers                                 │
  │                       │                                                  │
  └───────────────────────┼──────────────────────────────────────────────── ┘
                          │ (only for sources behind a Cloudflare challenge)
                   flaresolverr  (headless-browser solver, LAN-internal)
```

The browser calls `location.origin + '/api'`; **Caddy** forwards it to the local **helper**. The
helper (the JVM Kotatsu engine) fetches and parses the manga site server-side and returns clean
JSON + image URLs. The web UI never scrapes anything itself.

## Is it really fully local?

**Reading is 100% local.** The SPA talks only to the in-container helper, which parses each site
itself — nothing about what you read leaves your machine.

The one optional remote piece is **library sync** (`NYORA_SYNC_URL`):

| Choice | How | Local? |
|---|---|---|
| **Don't sign in** | Use it as a guest — everything stays on your device | ✅ 100% local |
| **Self-host sync** | Run your own [`nyora-sync-server`](https://github.com/Hasan72341/Nyora) and set `NYORA_SYNC_URL` | ✅ 100% local |
| **Use Nyora Cloud** | Leave the default to sync with the other Nyora apps | ☁️ sync only |

## Configuration

Copy `.env.example` → `.env` (Compose) or pass `-e` flags (`docker run`):

| Variable / setting | Default | Purpose |
|---|---|---|
| host port | `8080` | `-p <host>:8080` or `WEB_PORT` with Compose |
| `/data` volume | — | persists your **library, history, settings + SQLite DB** |
| `NYORA_SYNC_URL` | `https://sync.nyora.xyz` | optional sync backend (self-host / omit) |
| `FLARESOLVERR_URL` | `http://flaresolverr:8191/v1` | CF-challenge solver (Compose sets this) |
| `NYORA_HELPER_PORT` | `8788` | loopback port the helper binds (behind `/api`) |
| `JAVA_OPTS` | `-Xmx320m …` | JVM tuning for the helper |
| `NYORA_WEB_REF` | `main` | *(build only)* git ref of [`nyora-web`](https://github.com/Nyora-Manga/nyora-web) |
| `NYORA_SHARED_REF` | `main` | *(build only)* git ref of [`nyora-shared`](https://github.com/Nyora-Manga/nyora-shared) |

## Behind a reverse proxy / HTTPS

The container serves plain HTTP on `:8080` and computes its API base from the **request origin**,
so it works behind any TLS-terminating proxy with **no extra config**. Example (Caddy):

```caddy
manga.example.com {
    reverse_proxy localhost:8080
}
```
Traefik/nginx are equivalent — just proxy your hostname to the container's `:8080`.

## Updating

```sh
docker compose pull && docker compose up -d      # from Docker Hub
# or, from source:
docker compose build --no-cache && docker compose up -d
```
Your `/data` (library, history, settings) survives updates. To pin exact versions when building:
`NYORA_WEB_REF=web-v2.0.0 NYORA_SHARED_REF=main docker compose build`.

## Build from source

Three stages, all from source (no prebuilt binaries): **(1) helper** — clones
[`nyora-shared`](https://github.com/Nyora-Manga/nyora-shared) and builds the Kotatsu-engine fat jar
with a small vendored Gradle project in [`helper/`](./helper); **(2) web** — clones
[`nyora-web`](https://github.com/Nyora-Manga/nyora-web) and runs `npm run build`; **(3) runtime** —
a lean `eclipse-temurin:17-jre` + Caddy, running **non-root**, healthchecked.

## Troubleshooting

- **`docker pull` fails on an amd64 machine** — the published `latest` is currently **linux/arm64**.
  A multi-arch (amd64 + arm64) image is planned; until then, build from source on amd64 (`docker compose up --build`).
- **A source shows no results / images** — it may need the Cloudflare solver: run with **Compose**
  (Option B) so FlareSolverr is available. The helper degrades gracefully without it.
- **A CSS/console error after an update** — the web app is a PWA; hard-reload (**Ctrl/Cmd + Shift + R**)
  once to flush the service-worker cache.
- **Port already in use** — change the host port: `-p 9000:8080` or `WEB_PORT=9000 docker compose up -d`.
- **Health** — `curl http://localhost:8080/api/health` should return `{"status":"ok"}`.
- **Logs** — `docker logs -f nyora` (or `docker compose logs -f nyora`).

## FAQ

**How do I self-host a manga reader with Docker?**
`docker run -d -p 8080:8080 -v nyora-data:/data hasan72341/nyora` → open `http://localhost:8080`.
It bundles the reader UI and the parser engine, so there's nothing else to set up.

**Is this a self-hosted Tachiyomi / Mihon / Kotatsu alternative?**
Yes — a fully-local, ad-free reader you run yourself. It's built on the open-source **Kotatsu**
parser engine and adds a browser UI, so you read hundreds of sources from your own server.

**Does it phone home / need an account?**
No. Reading is entirely local (the in-container helper parses everything). No account is needed;
optional cross-device sync is the only remote piece, and you can self-host or skip it.

**Where is my library stored?** In the `/data` Docker volume — library, history, settings and the
SQLite DB. Back it up by copying that volume.

**Which architectures are supported?** The `latest` image is `linux/arm64` today; build from source
for amd64, or watch for the multi-arch build.

**Can I put it behind HTTPS / a domain?** Yes — proxy your hostname to the container's `:8080`
with Caddy/Traefik/nginx. The app adapts to whatever origin it's served from.

**Manga, manhwa or manhua?** All three — the Kotatsu engine covers Japanese, Korean and Chinese
comics across many languages.

## Requirements & notes

- **Docker** + the Compose plugin. ~1.5 GB RAM for the full stack (`nyora` 768 MB, `flaresolverr` 700 MB).
- On **Apple Silicon / ARM**, the FlareSolverr image is amd64 and runs under emulation (slower to warm).
- Parser engine: the open-source [Kotatsu](https://github.com/KotatsuApp/kotatsu-parsers) engine.
  Web UI: [`nyora-web`](https://github.com/Nyora-Manga/nyora-web).

## Related

- **Docker Hub image:** [`hasan72341/nyora`](https://hub.docker.com/r/hasan72341/nyora)
- **Nyora project index (every repo):** https://github.com/Hasan72341/Nyora
- **Apps for Android / iOS / macOS / Windows / Linux + the hosted web app:** https://nyora.pages.dev
- **Developer SDKs:** [`nyora`](https://pypi.org/project/nyora/) (Python) · [`nyora-sdk`](https://www.npmjs.com/package/nyora-sdk) (JS/TS)

## License

Apache-2.0. Built on the open-source Kotatsu parser engine; not affiliated with Tachiyomi, Mihon or Kotatsu.
