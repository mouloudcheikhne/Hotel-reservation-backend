# Admin User API

Base path: `/api/admin/users`

**Access requirements**
- Role: `ROLE_ADMIN`
- Include `Authorization: Bearer <token>` header with an admin JWT.

## GET /api/admin/users
- **Description**: Lists every user account.
- **Responses**:
  - `200 OK` with an array of `User` objects.
  - `404 NOT FOUND` if the table is empty.

```bash
curl -X GET "http://localhost:8093/api/admin/users" \
  -H "Authorization: Bearer $TOKEN"
```

---

## GET /api/admin/users/me
- **Description**: Returns the authenticated principal as seen by Spring Security.
- **Responses**:
  - `200 OK` with the `CustomUserDetails` object for the active token.

```bash
curl -X GET "http://localhost:8093/api/admin/users/me" \
  -H "Authorization: Bearer $TOKEN"
```

---

## POST /api/admin/users/add
- **Description**: Creates a new user with the supplied role.
- **Request body (JSON)**:
  - `nom` *(string, required)*
  - `prenom` *(string, required)*
  - `email` *(string, required, unique, valid email)*
  - `password` *(string, required)*
  - `role` *(string, required)* — one of `USER`, `ADMIN`, `RESEPTION`.
- **Responses**:
  - `200 OK` with the persisted `User` (password is hashed before saving).
  - `400 BAD REQUEST` if the email is already registered.

```bash
curl -X POST "http://localhost:8093/api/admin/users/add" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
        "nom": "Smith",
        "prenom": "Alex",
        "email": "alex.smith@example.com",
        "password": "AdminPassword456",
        "role": "RESEPTION"
      }'
```

---

## PUT /api/admin/users/update/{id}
- **Description**: Partially updates the selected user. Fields omitted from the payload remain unchanged.
- **Path params**:
  - `id` *(long, required)*
- **Request body (JSON)**: any combination of
  - `nom` *(string, optional)*
  - `prenom` *(string, optional)*
  - `email` *(string, optional, must stay unique)*
  - `password` *(string, optional, plain text — service hashes it)*
  - `role` *(string, optional)* — one of `USER`, `ADMIN`, `RESEPTION`.
- **Responses**:
  - `200 OK` with the updated `User`.
  - `400 BAD REQUEST` if the new email collides with another account.
  - `404 NOT FOUND` if the user ID is missing.

```bash
curl -X PUT "http://localhost:8093/api/admin/users/update/8" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
        "prenom": "Alexis",
        "role": "ADMIN"
      }'
```

---

## DELETE /api/admin/users/delete/{id}
- **Description**: Removes the specified user account.
- **Path params**:
  - `id` *(long, required)*
- **Responses**:
  - `200 OK` with `{ "message": "User deleted successfully" }`.
  - `404 NOT FOUND` if the user is not present.

```bash
curl -X DELETE "http://localhost:8093/api/admin/users/delete/8" \
  -H "Authorization: Bearer $TOKEN"
```
