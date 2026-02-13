# Public Rooms API

Base path: `/api/rooms`

## GET /api/rooms
- **Access**: Public
- **Description**: Retrieves all rooms marked as available.
- **Query params**: none
- **Responses**:
  - `200 OK` with an array of `Room` objects.
  - `404 NOT FOUND` with `ApiError` if no rooms are available.

```bash
curl -X GET "http://localhost:8093/api/rooms"
```

**Room example**
```json
{
  "id": 12,
  "roomNumber": 204,
  "type": "DOUBLE",
  "price": 89.5,
  "available": true,
  "description": "Spacious double room with balcony",
  "imageUrl": "/uploads/rooms/2a4f-...-room.jpg"
}
```

---

## GET /api/rooms/dates-reserved/{roomId}
- **Access**: Public
- **Description**: Lists reserved date ranges for the specified room, regardless of booking status.
- **Path params**:
  - `roomId` *(long, required)* — ID of the room.
- **Responses**:
  - `200 OK` with an array of `{ "startDate", "endDate" }` pairs (empty array if no bookings).
  - `404 NOT FOUND` if the room does not exist.

```bash
curl -X GET "http://localhost:8093/api/rooms/dates-reserved/12"
```

**Response example**
```json
[
  {
    "startDate": "2026-03-01",
    "endDate": "2026-03-05"
  },
  {
    "startDate": "2026-03-20",
    "endDate": "2026-03-22"
  }
]
```
