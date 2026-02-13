package hotel.example.hotelreservaion.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.example.hotelreservaion.dto.AddBookingDto;
import hotel.example.hotelreservaion.dto.ChangeStatusBookingDto;
import hotel.example.hotelreservaion.service.admin.BookingService;

@RestController
@RequestMapping("/api/admin/bookings")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    // Get all bookings
    @GetMapping("")
    public ResponseEntity<?> getAllBookings(){
        return bookingService.getAllBookings();
    }
    
    // Get booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id){
        return bookingService.getBookingById(id);
    }
    
    // Create new booking
    @PostMapping("/add")
    public ResponseEntity<?> addBooking(@RequestBody AddBookingDto data){
        return bookingService.addBooking(data);
    }
    
    // Update booking status
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBookingStatus(@PathVariable Long id, @RequestBody ChangeStatusBookingDto data){
        return bookingService.updateBookingStatus(id, data);
    }
    
    // Delete booking
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id){
        return bookingService.deleteBooking(id);
    }
}
