package hotel.example.hotelreservaion.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hotel.example.hotelreservaion.dto.AddBookingDto;
import hotel.example.hotelreservaion.dto.ChangeStatusBookingDto;
import hotel.example.hotelreservaion.exception.CustomException;
import hotel.example.hotelreservaion.model.Booking;
import hotel.example.hotelreservaion.model.BookingStatus;
import hotel.example.hotelreservaion.model.Room;
import hotel.example.hotelreservaion.model.User;
import hotel.example.hotelreservaion.repository.BookingRepo;
import hotel.example.hotelreservaion.repository.RoomRepo;
import hotel.example.hotelreservaion.repository.UserReposiory;

@Service
public class ReseptionService {

    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private UserReposiory userRepo;

    public ResponseEntity<?> getAllRoomAvailable() {
        try {
            List<Room> rooms = roomRepo.findByAvailable(true);
            if(rooms.isEmpty()){
                throw new CustomException("No rooms available", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(rooms);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while getting all room available"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    public ResponseEntity<?>getallRoomReserved(){
        try{
            List<Room> rooms = roomRepo.findByAvailable(false);
            if(rooms.isEmpty()){
                throw new CustomException("No rooms reserved", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(rooms);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while getting all room reserved"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> getallBookingReserved(BookingStatus status){
        try{

            List<Booking> bookings = bookingRepo.findByStatusAndEndDateAfter(status, new Date(System.currentTimeMillis()));
            if(bookings.isEmpty()){
                throw new CustomException("No bookings reserved", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(bookings);
        }
        catch(CustomException e){
            throw e;
        }
        catch(IllegalArgumentException e){
            throw new CustomException("Invalid booking status", HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            throw new CustomException("An error occurred while getting all booking reserved"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    // Get all bookings with end date greater than today (not finished)
    public ResponseEntity<?> getAllBookingsNotFinished(){
        try{
            List<Booking> bookings = bookingRepo.findAll();
            if(bookings.isEmpty()){
                throw new CustomException("No bookings found", HttpStatus.NOT_FOUND);
            }
            
            // Filter bookings where endDate > today
            java.util.Date today = new java.util.Date();
            Date todayDate = new Date(today.getTime());
            
            List<Booking> activeBookings = bookings.stream()
                    .filter(booking -> booking.getEndDate().after(todayDate))
                    .toList();
            
            if(activeBookings.isEmpty()){
                throw new CustomException("No active bookings found", HttpStatus.NOT_FOUND);
            }
            
            return ResponseEntity.ok(activeBookings);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while getting active bookings: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Add booking
    public ResponseEntity<?> addBooking(AddBookingDto data){
        try{
            // Validate user exists
            Optional<User> user = userRepo.findById(data.getUserId());
            if(user.isEmpty()){
                throw new CustomException("User not found", HttpStatus.NOT_FOUND);
            }
            
            // Validate room exists
            Optional<Room> room = roomRepo.findById(data.getRoomId());
            if(room.isEmpty()){
                throw new CustomException("Room not found", HttpStatus.NOT_FOUND);
            }
            
            // Validate dates
            if(data.getStartDate() == null || data.getEndDate() == null){
                throw new CustomException("Start date and end date are required", HttpStatus.BAD_REQUEST);
            }
            
            if(data.getStartDate().after(data.getEndDate())){
                throw new CustomException("Start date must be before end date", HttpStatus.BAD_REQUEST);
            }
            
            // Check if room is available - get all bookings for this room
            Room roomObj = room.get();
            List<Booking> roomBookings = bookingRepo.findByRoom(roomObj);
            
            // Check for date conflicts with existing bookings
            for(Booking existingBooking : roomBookings){
                // Conflict if: newStart < existingEnd AND newEnd > existingStart
                if(data.getStartDate().before(existingBooking.getEndDate()) && 
                   data.getEndDate().after(existingBooking.getStartDate())){
                    throw new CustomException("Room is not available for the selected dates", HttpStatus.BAD_REQUEST);
                }
            }
            
            // Calculate total price
            long timeDiff = data.getEndDate().getTime() - data.getStartDate().getTime();
            Integer days = (int) (timeDiff / (1000 * 60 * 60 * 24));
            double totalPrice = roomObj.getPrice() * days;
            
            // Create new booking
            Booking newBooking = Booking.builder()
                    .user(user.get())
                    .room(room.get())
                    .startDate(data.getStartDate())
                    .endDate(data.getEndDate())
                    .totalPrice(totalPrice)
                    .status(BookingStatus.PENDING)
                    .build();
            
            Booking savedBooking = bookingRepo.save(newBooking);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while adding the booking: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Update booking
    public ResponseEntity<?> updateBooking(Long id, ChangeStatusBookingDto data){
        try{
            Optional<Booking> booking = bookingRepo.findById(id);
            if(booking.isEmpty()){
                throw new CustomException("Booking not found", HttpStatus.NOT_FOUND);
            }
            
            Booking existingBooking = booking.get();
            
            // Check if booking is already finished
            java.util.Date today = new java.util.Date();
            if(existingBooking.getEndDate().before(new Date(today.getTime()))){
                throw new CustomException("Cannot update a booking that is already finished", HttpStatus.BAD_REQUEST);
            }
            
            // Update status only if provided
            if(data.getStatus() != null){
                existingBooking.setStatus(data.getStatus());
            }
            
            // Update dates if provided
            if(data.getStartDate() != null && data.getEndDate() != null){
                // Validate dates
                if(data.getStartDate().after(data.getEndDate())){
                    throw new CustomException("Start date must be before end date", HttpStatus.BAD_REQUEST);
                }
                
                // Check for conflicts with other bookings
                Room roomObj = existingBooking.getRoom();
                List<Booking> roomBookings = bookingRepo.findByRoom(roomObj);
                
                // Check for date conflicts with other bookings (excluding current booking)
                for(Booking otherBooking : roomBookings){
                    if(otherBooking.getId().equals(id)){
                        continue; // Skip current booking
                    }
                    // Conflict if: newStart < otherEnd AND newEnd > otherStart
                    if(data.getStartDate().before(otherBooking.getEndDate()) && 
                       data.getEndDate().after(otherBooking.getStartDate())){
                        throw new CustomException("Room is not available for the selected dates", HttpStatus.BAD_REQUEST);
                    }
                }
                
                // Update dates and recalculate price
                existingBooking.setStartDate(data.getStartDate());
                existingBooking.setEndDate(data.getEndDate());
                
                long timeDiff = data.getEndDate().getTime() - data.getStartDate().getTime();
                Integer days = (int) (timeDiff / (1000 * 60 * 60 * 24));
                double totalPrice = roomObj.getPrice() * days;
                existingBooking.setTotalPrice(totalPrice);
            }
            
            Booking updatedBooking = bookingRepo.save(existingBooking);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Booking updated successfully");
            response.put("booking", updatedBooking);
            return ResponseEntity.ok(response);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while updating the booking: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Delete booking
    public ResponseEntity<?> deleteBooking(Long id){
        try{
            Optional<Booking> booking = bookingRepo.findById(id);
            if(booking.isEmpty()){
                throw new CustomException("Booking not found", HttpStatus.NOT_FOUND);
            }
            
            Booking existingBooking = booking.get();
            
            // Check if booking is already finished
            java.util.Date today = new java.util.Date();
            if(existingBooking.getEndDate().before(new Date(today.getTime()))){
                throw new CustomException("Cannot delete a booking that is already finished", HttpStatus.BAD_REQUEST);
            }
            
            bookingRepo.deleteById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Booking deleted successfully");
            return ResponseEntity.ok(response);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while deleting the booking: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Change booking status only
    public ResponseEntity<?> changeBookingStatus(Long id, BookingStatus status){
        try{
            Optional<Booking> booking = bookingRepo.findById(id);
            if(booking.isEmpty()){
                throw new CustomException("Booking not found", HttpStatus.NOT_FOUND);
            }
            
            Booking existingBooking = booking.get();
            
            // Check if booking is already finished
            java.util.Date today = new java.util.Date();
            if(existingBooking.getEndDate().before(new Date(today.getTime()))){
                throw new CustomException("Cannot change status of a booking that is already finished", HttpStatus.BAD_REQUEST);
            }
            
            existingBooking.setStatus(status);
            Booking updatedBooking = bookingRepo.save(existingBooking);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Booking status changed successfully");
            response.put("booking", updatedBooking);
            return ResponseEntity.ok(response);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while changing booking status: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

}
