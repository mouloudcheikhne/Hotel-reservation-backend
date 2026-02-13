# Client API

Base path: `/api/client`

**Access requirements**
- Role: `ROLE_USER`
- Include `Authorization: Bearer <token>` header using the JWT obtained from `/api/auth/login`.

## GET /api/client/get-all-bookings
- **Description**: Returns every booking in the system. No filtering by user is applied.
- **Responses**:
  - `200 OK` with an array of `Booking` objects.
  - `404 NOT FOUND` with `ApiError` when no bookings exist.

```bash
curl -X GET "http://localhost:8093/api/client/get-all-bookings" \
  -H "Authorization: Bearer $TOKEN"
```

**Booking example**
```json
{
  "id": 7,
  "user": {
    "id": 3,
    "nom": "Doe",
    "prenom": "Jane",
    "email": "jane.doe@example.com",
    "role": "USER"
  },
  "room": {
    "id": 12,
    "roomNumber": 204,
    "type": "DOUBLE",
    "price": 89.5,
    "available": false,
    "description": "Spacious double room",
    "imageUrl": "/uploads/rooms/2a4f-room.jpg"
  },
  "startDate": "2026-04-01",
  "endDate": "2026-04-04",
  "totalPrice": 268.5,
  "status": "PENDING"
}
```

---

## POST /api/client/add-booking
- **Description**: Creates a booking for the authenticated user. The service enforces that the room exists, is marked available, and that dates are valid.
- **Request body (JSON)**:
  - `roomId` *(long, required)*
  - `startDate` *(ISO date, required, must be today or later)*
  - `endDate` *(ISO date, required, must be after `startDate`)*
- **Responses**:
  - `200 OK` with the persisted `Booking` (status defaults to `PENDING`).
  - `400 BAD REQUEST` with `ApiError` if business rules fail (room unavailable, invalid dates, etc.).
  - `404 NOT FOUND` if the room or user cannot be resolved.

```bash
curl -X POST "http://localhost:8093/api/client/add-booking" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
        "roomId": 12,
        "startDate": "2026-04-15",
        "endDate": "2026-04-18"
      }'
```

---

## PUT /api/client/change-booking-status/{bookingId}
- **Description**: Updates the status of the target booking.
- **Path params**:
  - `bookingId` *(long, required)*
- **Request body (JSON)**:
  - `status` *(string, required)* — must be one of `PENDING`, `CONFIRMED`, `CANCELLED`.
- **Responses**:
  - `200 OK` with the updated `Booking`.
  - `404 NOT FOUND` if the booking does not exist.

```bash
curl -X PUT "http://localhost:8093/api/client/change-booking-status/7" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
        "status": "CONFIRMED"
      }'
```
