# Auth API

Base path: `/api/auth`

## GET /api/auth/test
- **Access**: Public
- **Description**: Quick health check that returns a static welcome string.
- **Query params**: none
- **Responses**:
  - `200 OK` with plain text greeting.

```bash
curl -X GET "http://localhost:8093/api/auth/test"
```

---

## POST /api/auth/register
- **Access**: Public
- **Description**: Creates a new end-user account with role `USER`.
- **Request body (JSON)**:
  - `nom` *(string, required)*
  - `prenom` *(string, required)*
  - `email` *(string, required, valid email)*
  - `password` *(string, required)*
- **Responses**:
  - `200 OK` with `ResponceRegesterDtO` payload.
  - `400 BAD REQUEST` with validation errors, unique email violation, or `ApiError` when email already exists.

```bash
curl -X POST "http://localhost:8093/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
        "nom": "Doe",
        "prenom": "Jane",
        "email": "jane.doe@example.com",
        "password": "StrongPassword123"
      }'
```

**Successful response body example**
```json
{
  "nom": "Doe",
  "prenom": "Jane",
  "email": "jane.doe@example.com",
  "role": "USER"
}
```

---

## POST /api/auth/login
- **Access**: Public
- **Description**: Authenticates user credentials and returns a JWT.
- **Request body (JSON)**:
  - `email` *(string, required, valid email)*
  - `password` *(string, required)*
- **Responses**:
  - `200 OK` with `ResponceLoginDto` containing JWT token and profile data.
  - `400 BAD REQUEST` when credentials are invalid (returns `ApiError`).

```bash
curl -X POST "http://localhost:8093/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
        "email": "jane.doe@example.com",
        "password": "StrongPassword123"
      }'
```

**Successful response body example**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "nom": "Doe",
  "prenom": "Jane",
  "email": "jane.doe@example.com",
  "role": "ROLE_USER"
}
```

Use the returned token as `Authorization: Bearer <token>` for protected endpoints.
