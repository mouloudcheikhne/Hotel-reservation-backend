package hotel.example.hotelreservaion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.example.hotelreservaion.dto.AddBookingDto;
import hotel.example.hotelreservaion.dto.ChangeStatusBookingDto;
import hotel.example.hotelreservaion.model.BookingStatus;
import hotel.example.hotelreservaion.service.ReseptionService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


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
    // @PostMapping("/bookings-status")
    // public ResponseEntity<?> getAllBookingsPending(@Valid @RequestBody ChangeStatusBookingDto status) {
    //     // return ResponseEntity.ok(status);
    //     return reseptionService.getallBookingReserved(status.getStatus());
    // }
    
    @GetMapping("/bookings/active")
    public ResponseEntity<?> getAllBookingsNotFinished(){
        return reseptionService.getAllBookingsNotFinished();
    }
    
    
    // Add booking
    @PostMapping("/bookings/add")
    public ResponseEntity<?> addBooking(@RequestBody AddBookingDto data){
        return reseptionService.addBooking(data);
    }
    
    // Update booking
    @PutMapping("/bookings/update/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody ChangeStatusBookingDto data){
        return reseptionService.updateBooking(id, data);
    }
    
    // Delete booking
    @DeleteMapping("/bookings/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id){
        return reseptionService.deleteBooking(id);
    }
    
    // Change booking status
    @PutMapping("/bookings/change-status/{id}")
    public ResponseEntity<?> changeBookingStatus(@PathVariable Long id, @RequestBody ChangeStatusBookingDto data){
        return reseptionService.changeBookingStatus(id, data.getStatus());
    }

}
