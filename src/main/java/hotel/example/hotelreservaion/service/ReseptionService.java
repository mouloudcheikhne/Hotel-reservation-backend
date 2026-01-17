package hotel.example.hotelreservaion.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hotel.example.hotelreservaion.exception.CustomException;
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

}
