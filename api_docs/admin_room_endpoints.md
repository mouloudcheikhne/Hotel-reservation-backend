# Admin Room API

Base path: `/api/admin/rooms`

**Access requirements**
- Role: `ROLE_ADMIN`
- Include `Authorization: Bearer <token>` header with an admin JWT.

## POST /api/admin/rooms/add
- **Description**: Creates a new room and uploads its image. The service stores the file in `uploads/rooms` and sets the room availability to `false` by default.
- **Request format**: `multipart/form-data`
  - `roomNumber` *(integer, required)*
  - `description` *(string, required)*
  - `type` *(string, required)*
  - `price` *(number, required)*
  - `image` *(file, required)*
- **Responses**:
  - `200 OK` with the persisted `Room` object.
  - `400 BAD REQUEST` if validations fail or the room number already exists.

```bash
curl -X POST "http://localhost:8093/api/admin/rooms/add" \
  -H "Authorization: Bearer $TOKEN" \
  -F roomNumber=305 \
  -F description="Suite with ocean view" \
  -F type=SUITE \
  -F price=249.99 \
  -F image=@suite.jpg
```

---

## PUT /api/admin/rooms/update/{id}
- **Description**: Partially updates the target room. Any omitted field remains unchanged. Supports optional image replacement.
- **Path params**:
  - `id` *(long, required)*
- **Request format**: `multipart/form-data`
  - `roomNumber` *(integer, optional)*
  - `description` *(string, optional)*
  - `type` *(string, optional)*
  - `price` *(number, optional)*
  - `image` *(file, optional)*
- **Responses**:
  - `200 OK` with `{ "message", "room" }` payload of the updated entity.
  - `400 BAD REQUEST` when the new room number already exists.
  - `404 NOT FOUND` if the room ID does not exist.

```bash
curl -X PUT "http://localhost:8093/api/admin/rooms/update/12" \
  -H "Authorization: Bearer $TOKEN" \
  -F price=189.0 \
  -F image=@updated-suite.jpg
```

---

## DELETE /api/admin/rooms/delete/{id}
- **Description**: Permanently removes the room.
- **Path params**:
  - `id` *(long, required)*
- **Responses**:
  - `200 OK` with `{ "message": "Room deleted successfully" }`.
  - `404 NOT FOUND` if no room matches the ID.

```bash
curl -X DELETE "http://localhost:8093/api/admin/rooms/delete/12" \
  -H "Authorization: Bearer $TOKEN"
```

---

## GET /api/admin/rooms
- **Description**: Returns every room, regardless of availability.
- **Responses**:
  - `200 OK` with an array of `Room` objects.
  - `404 NOT FOUND` when there are no rooms in the database.

```bash
curl -X GET "http://localhost:8093/api/admin/rooms" \
  -H "Authorization: Bearer $TOKEN"
```

---

## PUT /api/admin/rooms/change-status/{id}
- **Description**: Toggles the `available` flag for the specified room.
- **Path params**:
  - `id` *(long, required)*
- **Responses**:
  - `200 OK` with `{ "message", "room" }` showing the updated availability.
  - `404 NOT FOUND` if the room does not exist.

```bash
curl -X PUT "http://localhost:8093/api/admin/rooms/change-status/12" \
  -H "Authorization: Bearer $TOKEN"
```
