package hotel.example.hotelreservaion.service;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hotel.example.hotelreservaion.dto.AddBookingDto;
import hotel.example.hotelreservaion.model.Booking;
import hotel.example.hotelreservaion.model.BookingStatus;
import hotel.example.hotelreservaion.model.Room;
import hotel.example.hotelreservaion.model.User;
import hotel.example.hotelreservaion.repository.BookingRepo;
import hotel.example.hotelreservaion.repository.RoomRepo;
import hotel.example.hotelreservaion.repository.UserReposiory;

@Service
public class ClientService {
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private UserReposiory userRepo;
    public ResponseEntity<?> getallBookings(){
        try{
            List<Booking> bookings=bookingRepo.findAll();
            return ResponseEntity.ok(bookings);

        }catch(Exception e){
            Map<String, String> errors=new HashMap<>();
            errors.put("message", "An error occurred while getting all bookings"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
        }
    }
    public ResponseEntity<?> addBooking(AddBookingDto data,String email){
        try{
            Optional<User> user=userRepo.findByEmail(email);
            Map<String, String> errors=new HashMap<>();
            if(!user.isPresent()){
                errors.put("message", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
            }
            Optional<Room> roomOptionel=roomRepo.findById(data.getRoomId());
            if(!roomOptionel.isPresent()){
                errors.put("message", "Room not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
            }
            Room room=roomOptionel.get();
            if(!room.getAvailable()){
                errors.put("message", "Room is not available");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }
            Date currentDate = new Date(System.currentTimeMillis());
            if(data.getStartDate().before(currentDate)){
                errors.put("message", "Start date must be in the future");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }
            if(data.getStartDate().after(data.getEndDate())){
                errors.put("message", "Start date must be before end date");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }
           

            long timeDiff = data.getEndDate().getTime() - data.getStartDate().getTime();
            Integer days = (int) (timeDiff / (1000 * 60 * 60 * 24));
            double totalPrice=room.getPrice()*days;
            Booking booking=Booking.builder().user(user.get()).room(room).startDate(data.getStartDate()).endDate(data.getEndDate()).totalPrice(totalPrice).status(BookingStatus.PENDING).build();
            // bookingRepo.save(booking);
            Booking savedBooking=bookingRepo.save(booking);
            return ResponseEntity.ok(savedBooking);
        }catch(Exception e){
            Map<String, String> errors=new HashMap<>();
            errors.put("message", "An error occurred while adding booking"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
        }
    }

    public ResponseEntity<?> changeBookingStatus(Long bookingId,BookingStatus status){
        try{
            Optional<Booking> bookingOptionel=bookingRepo.findById(bookingId);
            Map<String, String> errors=new HashMap<>();
            if(!bookingOptionel.isPresent()){
                errors.put("message", "Booking not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
            }
            Booking booking=bookingOptionel.get();
            booking.setStatus(status);
            Booking savedBooking=bookingRepo.save(booking);
            return ResponseEntity.ok(savedBooking);
        }catch(Exception e){
            Map<String, String> errors=new HashMap<>();
            errors.put("message", "An error occurred while changing booking status"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
        }
    }
}
