package hotel.example.hotelreservaion.service.admin;

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
import hotel.example.hotelreservaion.dto.AdminAnalysisDto;
import hotel.example.hotelreservaion.dto.ChangeStatusBookingDto;
import hotel.example.hotelreservaion.exception.CustomException;
import hotel.example.hotelreservaion.model.Booking;
import hotel.example.hotelreservaion.model.Room;
import hotel.example.hotelreservaion.model.User;
import hotel.example.hotelreservaion.repository.BookingRepo;
import hotel.example.hotelreservaion.repository.RoomRepo;
import hotel.example.hotelreservaion.repository.UserReposiory;
import hotel.example.hotelreservaion.model.BookingStatus;
@Service
public class BookingService {
    
    @Autowired
    private BookingRepo bookingRepo;
    
    @Autowired
    private UserReposiory userRepo;
    
    @Autowired
    private RoomRepo roomRepo;
    
    // Get all bookings
    public ResponseEntity<?> getAllBookings(){
        try{
            List<Booking> bookings = bookingRepo.findAll();
            if(bookings.isEmpty()){
                throw new CustomException("No bookings found", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(bookings);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while getting bookings: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get booking by ID
    public ResponseEntity<?> getBookingById(Long id){
        try{
            Optional<Booking> booking = bookingRepo.findById(id);
            if(booking.isEmpty()){
                throw new CustomException("Booking not found", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(booking.get());
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while getting the booking: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Create new booking
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
    
    // Update booking status and dates
    public ResponseEntity<?> updateBookingStatus(Long id, ChangeStatusBookingDto data){
        try{
            Optional<Booking> booking = bookingRepo.findById(id);
            if(booking.isEmpty()){
                throw new CustomException("Booking not found", HttpStatus.NOT_FOUND);
            }
            
            Booking existingBooking = booking.get();
            existingBooking.setStatus(data.getStatus());
            
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
    
    // Get all bookings for today
    public ResponseEntity<?> getAllBookingsToday(){
        try{
            java.util.Date today = new java.util.Date();
            Date todayDate = new Date(today.getTime());
            
            List<Booking> allBookings = bookingRepo.findAll();
            
            // Filter bookings where startDate == today
            List<Booking> todayBookings = allBookings.stream()
                    .filter(booking -> booking.getStartDate().equals(todayDate))
                    .toList();
            
            if(todayBookings.isEmpty()){
                throw new CustomException("No bookings for today", HttpStatus.NOT_FOUND);
            }
            
            return ResponseEntity.ok(todayBookings);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while getting bookings for today: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get analysis: statistics only (counts)
    public ResponseEntity<?> getAdminAnalysis(){
        try{
            // Use count queries instead of fetching all data
            long totalUsers = userRepo.count();
            long totalBookings = bookingRepo.count();
            long totalRooms = roomRepo.count();
            long reservedRooms = roomRepo.countByAvailable(false);
            
            AdminAnalysisDto analysis = AdminAnalysisDto.builder()
                    .totalUsers((int) totalUsers)
                    .totalBookings((int) totalBookings)
                    .totalRooms((int) totalRooms)
                    .reservedRooms((int) reservedRooms)
                    .build();
            
            return ResponseEntity.ok(analysis);
        }
        catch(Exception e){
            throw new CustomException("An error occurred while getting analysis: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
