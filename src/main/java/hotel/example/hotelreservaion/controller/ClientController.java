package hotel.example.hotelreservaion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.example.hotelreservaion.dto.AddBookingDto;
import hotel.example.hotelreservaion.dto.ChangeStatusBookingDto;

import hotel.example.hotelreservaion.model.CustomUserDetails;
import hotel.example.hotelreservaion.service.ClientService;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/get-all-bookings")
    public ResponseEntity<?> getAllBookings(){
        return clientService.getallBookings();
    }
    @PostMapping("/add-booking")
    public ResponseEntity<?> addBooking(@Valid @RequestBody AddBookingDto data){
       CustomUserDetails userDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       return clientService.addBooking(data, userDetails.getEmail());
      
        // return clientService.addBooking(data);
    }

    @PutMapping("/change-booking-status/{bookingId}")
    public ResponseEntity<?> changeBookingStatus(@Valid @PathVariable Long bookingId,@RequestBody ChangeStatusBookingDto status){
        return clientService.changeBookingStatus(bookingId, status.getStatus());
    }
}

