<div align="center">

<img src="https://nyora.pages.dev/icon.png" width="110" alt="Nyora" />

# Nyora — Docker

### Run the whole reader on your own machine.

A one-command, **fully local** Docker deployment of [Nyora](https://nyora.pages.dev) — it
bundles the **web interface** and the **parser helper** (the kotatsu-parsers engine) in one
container, so every source is parsed **on your machine**. Nothing talks to the hosted Nyora
cloud to read; the only optional remote piece is library sync, which you can self-host or skip.

<p>
  <img alt="Docker" src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" />
  <img alt="Self-hosted" src="https://img.shields.io/badge/Self--hosted-0AE448?style=for-the-badge&logo=serverless&logoColor=black" />
  <a href="./LICENSE"><img alt="License: Apache 2.0" src="https://img.shields.io/badge/License-Apache_2.0-blue.svg?style=for-the-badge" /></a>
</p>

</div>

---

## Quick start

### Option A — run the published image (fastest, no build)

The image is on Docker Hub as **[`hasan72341/nyora`](https://hub.docker.com/r/hasan72341/nyora)**:

```sh
git clone https://github.com/Hasan72341/nyora-docker.git
cd nyora-docker
docker compose pull && docker compose up -d
```

…or without cloning, just the reader (no FlareSolverr CF-solver):

```sh
docker run -d -p 8080:8080 -v nyora-data:/data hasan72341/nyora:latest
```

### Option B — build from source

```sh
git clone https://github.com/Hasan72341/nyora-docker.git
cd nyora-docker
docker compose up --build
```

Either way, open **http://localhost:8080**. Building from source takes a few minutes (it
compiles the parser engine and the web bundle); the published image starts in seconds.

```sh
WEB_PORT=9000 docker compose up -d          # serve on a different host port
docker compose down                          # stop
docker compose pull                          # update to the latest published image
```

## What runs, and where

Everything is **same-origin** inside one container — no CORS, no cloud, nothing to configure.

```
  ┌──────────────────────── nyora container (:8080) ────────────────────────┐
  │                                                                          │
  │   Caddy :8080                                                            │
  │     ├─ /*      → the web SPA (static files in /srv)                      │
  │     └─ /api/*  → 127.0.0.1:8788  (prefix stripped)                       │
  │                       │                                                  │
  │                 Java parser helper  (kotatsu-parsers engine +            │
  │                 NyoraRestServer)  — parses every source locally,         │
  │                 proxies covers/pages, handles hotlink referers           │
  │                       │                                                  │
  └───────────────────────┼──────────────────────────────────────────────── ┘
                          │ (only for sources behind a Cloudflare challenge)
                   flaresolverr  (headless-browser CF solver, LAN-internal)
```

- The browser calls `location.origin + '/api'`, Caddy forwards it to the local helper — so it
  works on `localhost`, a LAN IP, or behind your own HTTPS reverse proxy with **no** config.
- **FlareSolverr** is only used when a source hides behind a browser challenge; the helper
  degrades gracefully if it's unavailable.

## Is it *really* fully local?

**Reading is 100% local.** The SPA talks only to the in-container helper, which fetches and
parses each manga site itself (nothing routes through Nyora's servers).

The one optional remote piece is **library sync** (`NYORA_SYNC_URL`). You have three choices:

| Choice | How |
|---|---|
| **Don't sign in** | Use the app as a guest — everything stays on your device. Fully local. |
| **Self-host sync** | Run your own [`nyora-sync-server`](https://github.com/Hasan72341/Nyora) and set `NYORA_SYNC_URL` to it. Fully local. |
| **Use Nyora Cloud** | Leave the default `https://stream.hasanraza.tech` to sync with the other Nyora apps. |

## Configuration

Copy `.env.example` to `.env` (or pass vars inline):

| Variable | Default | Purpose |
|---|---|---|
| `WEB_PORT` | `8080` | Host port to serve on. |
| `NYORA_SYNC_URL` | `https://stream.hasanraza.tech` | Optional sync backend (see above). |
| `NYORA_WEB_REF` | `main` | Git ref of [`nyora-web`](https://github.com/Hasan72341/nyora-web) to build the SPA from. |
| `NYORA_SHARED_REF` | `main` | Git ref of [`nyora-shared`](https://github.com/Hasan72341/nyora-shared) to build the helper from. |

Your library, history and settings persist in the `nyora-data` Docker volume across restarts.

## How the image is built

Three stages, all **from source** (no prebuilt binaries):

1. **helper** — clones [`nyora-shared`](https://github.com/Hasan72341/nyora-shared) (the
   shared Kotlin engine) and builds `:shared:helperJar` (the fat jar with the kotatsu-parsers
   engine and the REST server) using the tiny self-contained Gradle project vendored in
   [`helper/`](./helper). **Only nyora-shared is fetched** — no desktop app, no Compose
   Desktop / Chromium artifacts.
2. **web** — clones `nyora-web`, runs `npm ci && npm run build` (esbuild) → `dist/`.
3. **runtime** — a lean `eclipse-temurin:17-jre` + Caddy, running as a **non-root** user, with
   a healthcheck that proves both the proxy and the helper are alive.

## Update to the latest Nyora

```sh
docker compose build --no-cache && docker compose up -d
```

Or pin specific versions, e.g. `NYORA_WEB_REF=web-v2.0.0 NYORA_SHARED_REF=main docker compose build`.

## Requirements & notes

- **Docker** + the Compose plugin. ~1.5 GB RAM for the stack (`nyora` 768 MB, `flaresolverr` 700 MB).
- On **Apple Silicon / ARM**, the FlareSolverr image is amd64 and runs under emulation — slower to
  warm up, but functional. Sources that need no CF solve are unaffected.
- The parser engine is the open-source **[Kotatsu](https://github.com/KotatsuApp/kotatsu-parsers)**
  engine; the web UI is [`nyora-web`](https://github.com/Hasan72341/nyora-web).

## Related

- **Nyora project index:** https://github.com/Hasan72341/Nyora
- **Website & apps:** https://nyora.pages.dev
- **Web UI source:** https://github.com/Hasan72341/nyora-web
- **Parser engine / helper:** https://github.com/Hasan72341/nyora-shared (the kotatsu-parsers engine + REST server)

## License

Apache-2.0. Nyora is built on the open-source Kotatsu parser engine and is not affiliated with
Tachiyomi, Mihon or Kotatsu.
