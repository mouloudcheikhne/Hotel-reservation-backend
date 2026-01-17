package hotel.example.hotelreservaion.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hotel.example.hotelreservaion.model.Room;
import hotel.example.hotelreservaion.repository.BookingRepo;
import hotel.example.hotelreservaion.repository.RoomRepo;

@Service
public class ReseptionService {

    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private BookingRepo bookingRepo;

    public ResponseEntity<?> getAllRoomAvailable() {
        try {
            Map<String, String> errors=new HashMap<>();
            List<Room> rooms = roomRepo.findByAvailable(true);
            if(rooms.isEmpty()){
                errors.put("message", "No rooms available");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
            }
            return ResponseEntity.ok(rooms);
        } catch (Exception e) {
            Map<String, String> errors=new HashMap<>();
            errors.put("message", "An error occurred while getting all room available"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }
}
}
