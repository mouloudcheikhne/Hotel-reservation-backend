package hotel.example.hotelreservaion.service;

import java.sql.Date;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hotel.example.hotelreservaion.dto.AddBookingDto;
import hotel.example.hotelreservaion.exception.CustomException;
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
    public ResponseEntity<?> getallRooms(){
        try{
            List<Room> rooms=roomRepo.findByAvailable(true);
            if(rooms.isEmpty()){
                throw new CustomException("No rooms found", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(rooms);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while getting all rooms"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> getallBookings(){
        try{
            List<Booking> bookings=bookingRepo.findAll();
            if(bookings.isEmpty()){
                throw new CustomException("No bookings found", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(bookings);

        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while getting all bookings"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> addBooking(AddBookingDto data,String email){
        try{
            Optional<User> user=userRepo.findByEmail(email);
            if(!user.isPresent()){
                throw new CustomException("User not found", HttpStatus.NOT_FOUND);
            }
            Optional<Room> roomOptionel=roomRepo.findById(data.getRoomId());
            if(!roomOptionel.isPresent()){
                throw new CustomException("Room not found", HttpStatus.NOT_FOUND);
            }
            Room room=roomOptionel.get();
            if(!room.getAvailable()){
                throw new CustomException("Room is not available", HttpStatus.BAD_REQUEST);
            }
            Date currentDate = new Date(System.currentTimeMillis());
            if(data.getStartDate().before(currentDate)){
                throw new CustomException("Start date must be in the future", HttpStatus.BAD_REQUEST);
            }
            if(data.getStartDate().after(data.getEndDate())){
                throw new CustomException("Start date must be before end date", HttpStatus.BAD_REQUEST);
            }
           

            long timeDiff = data.getEndDate().getTime() - data.getStartDate().getTime();
            Integer days = (int) (timeDiff / (1000 * 60 * 60 * 24));
            double totalPrice=room.getPrice()*days;
            Booking booking=Booking.builder().user(user.get()).room(room).startDate(data.getStartDate()).endDate(data.getEndDate()).totalPrice(totalPrice).status(BookingStatus.PENDING).build();
            // bookingRepo.save(booking);
            Booking savedBooking=bookingRepo.save(booking);
            room.setAvailable(false);
            roomRepo.save(room);
            return ResponseEntity.ok(savedBooking);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while adding booking"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> changeBookingStatus(Long bookingId,BookingStatus status){
        try{
            Optional<Booking> bookingOptionel=bookingRepo.findById(bookingId);
            if(!bookingOptionel.isPresent()){
                throw new CustomException("Booking not found", HttpStatus.NOT_FOUND);
            }
            Booking booking=bookingOptionel.get();
            booking.setStatus(status);
            Booking savedBooking=bookingRepo.save(booking);
            return ResponseEntity.ok(savedBooking);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while changing booking status"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
