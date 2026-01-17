package hotel.example.hotelreservaion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.example.hotelreservaion.dto.ChangeStatusBookingDto;
import hotel.example.hotelreservaion.model.BookingStatus;
import hotel.example.hotelreservaion.service.ReseptionService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/reseption")
public class ReceptionController {
    @Autowired
    private ReseptionService reseptionService;
    
    @GetMapping("/rooms-available")
    public ResponseEntity<?> getAllRoomAvailable() {
        return reseptionService.getAllRoomAvailable();
    }
    @GetMapping("/rooms-reserved")
    public ResponseEntity<?> getAllRoomReserved() {
        return reseptionService.getallRoomReserved();
    }
    @PostMapping("/bookings-status")
    public ResponseEntity<?> getAllBookingsPending(@Valid @RequestBody ChangeStatusBookingDto status) {
        // return ResponseEntity.ok(status);
        return reseptionService.getallBookingReserved(status.getStatus());
    }

    
    
}
