# Reception API

Base path: `/api/reseption`

**Access requirements**
- Role: `ROLE_RESEPTION`
- Include `Authorization: Bearer <token>` header with a JWT for a reception staff account.

## GET /api/reseption/rooms-available
- **Description**: Lists all rooms currently marked as available.
- **Responses**:
  - `200 OK` with an array of `Room` objects.
  - `404 NOT FOUND` with `ApiError` when no rooms are available.

```bash
curl -X GET "http://localhost:8093/api/reseption/rooms-available" \
  -H "Authorization: Bearer $TOKEN"
```

---

## GET /api/reseption/rooms-reserved
- **Description**: Lists all rooms currently marked as not available.
- **Responses**:
  - `200 OK` with an array of `Room` objects.
  - `404 NOT FOUND` if every room is available.

```bash
curl -X GET "http://localhost:8093/api/reseption/rooms-reserved" \
  -H "Authorization: Bearer $TOKEN"
```

---

## POST /api/reseption/bookings-status
- **Description**: Retrieves bookings filtered by status where the end date is still in the future.
- **Request body (JSON)**:
  - `status` *(string, required)* — one of `PENDING`, `CONFIRMED`, `CANCELLED`.
- **Responses**:
  - `200 OK` with an array of `Booking` records matching the filter.
  - `400 BAD REQUEST` if the status string cannot be parsed.
  - `404 NOT FOUND` if no bookings match.

```bash
curl -X POST "http://localhost:8093/api/reseption/bookings-status" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
        "status": "CONFIRMED"
      }'
```
