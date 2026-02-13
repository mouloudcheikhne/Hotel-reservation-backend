# API Documentation Index

These reference guides describe every REST controller exposed by the hotel reservation backend. Each Markdown file focuses on a specific role or functional area and includes request/response details plus ready-to-run `curl` snippets.

## Base URL
- Local development: `http://localhost:8093`
- All examples assume HTTPS is not enabled; prefix with `https://` when routing through a secure gateway.

## Files
- `auth_endpoints.md` – authentication flows (test, register, login).
- `public_rooms_endpoints.md` – public room discovery without authentication.
- `client_endpoints.md` – guest-oriented booking actions (requires `ROLE_USER`).
- `reception_endpoints.md` – reception desk operations (requires `ROLE_RESEPTION`).
- `admin_room_endpoints.md` – room inventory management (requires `ROLE_ADMIN`).
- `admin_user_endpoints.md` – administrative user management (requires `ROLE_ADMIN`).

## Usage Tips
- Acquire a JWT via `/api/auth/login`, then export it: `export TOKEN="<jwt>"` to reuse in subsequent commands.
- Replace sample IDs (e.g., room `12`, booking `7`) with real identifiers from your database.
- File upload examples rely on `curl -F`; ensure the referenced images exist relative to your shell.
