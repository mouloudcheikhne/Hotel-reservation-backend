package hotel.example.hotelreservaion.service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hotel.example.hotelreservaion.dto.ResponceGetDatesReserved;
import hotel.example.hotelreservaion.exception.CustomException;
import hotel.example.hotelreservaion.model.Room;
import hotel.example.hotelreservaion.repository.BookingRepo;
import hotel.example.hotelreservaion.repository.RoomRepo;
@Service
public class PublicService {
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private BookingRepo bookingRepo;
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

    public ResponseEntity<?> getDatesReserved(Long roomId){
        try{
            Optional<Room> roomOptionel=roomRepo.findById(roomId);
            if(!roomOptionel.isPresent()){
                throw new CustomException("Room not found", HttpStatus.NOT_FOUND);
            }
            Room room=roomOptionel.get();
            List<ResponceGetDatesReserved> datesReserved=bookingRepo.findByRoom(room).stream().map(booking -> ResponceGetDatesReserved.builder().startDate(booking.getStartDate()).endDate(booking.getEndDate()).build()).collect(Collectors.toList());
            if(datesReserved.isEmpty()){
                return ResponseEntity.ok(new ArrayList<>());
            }
            return ResponseEntity.ok(datesReserved);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while getting dates reserved"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}
