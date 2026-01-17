package hotel.example.hotelreservaion.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hotel.example.hotelreservaion.dto.AddRomeRequestDto;
import hotel.example.hotelreservaion.dto.UpdateRomeDto;
import hotel.example.hotelreservaion.exception.CustomException;
import hotel.example.hotelreservaion.model.Room;
import hotel.example.hotelreservaion.repository.RoomRepo;

@Service
public class RoomService {
    @Autowired
    private RoomRepo roomRepo;
    public ResponseEntity<?> getAllRooms(){
        try{
            List<Room> rooms=roomRepo.findAll();
            if(rooms.isEmpty()){
                throw new CustomException("No rooms found", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(rooms);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while getting the rooms"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> addRoom(AddRomeRequestDto data){
        try{
            boolean exists=roomRepo.existsByRoomNumber(data.getRoomNumber());
            if(exists){
                throw new CustomException("Room already exists", HttpStatus.BAD_REQUEST);
            }
            Room newRoom=Room.builder().roomNumber(data.getRoomNumber()).type(data.getType()).price(data.getPrice()).available(false).description(data.getDescription()).imageUrl(data.getImageUrl()).build();
            Room savedRoom=roomRepo.save(newRoom);
            return ResponseEntity.ok(savedRoom);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while adding the room"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateRoom(UpdateRomeDto data,Long id){
        try{
            Optional<Room> room=roomRepo.findById(id);
            if(room.isEmpty()){
                throw new CustomException("Room not found", HttpStatus.NOT_FOUND);
            }
            Room existingRoom=room.get();
            if(data.getRoomNumber()!=null){
                boolean exists=roomRepo.existsByRoomNumber(data.getRoomNumber());
                if(exists){
                    throw new CustomException("Room number already exists", HttpStatus.BAD_REQUEST);
                }
                existingRoom.setRoomNumber(data.getRoomNumber());
            }
            if(data.getDescription()!=null){
                existingRoom.setDescription(data.getDescription());
            }
            if(data.getType()!=null ){
                existingRoom.setType(data.getType());
            }
            if(data.getPrice()!=null && data.getPrice()>0){
                existingRoom.setPrice(data.getPrice());
            }
            if(data.getImageUrl()!=null){
                existingRoom.setImageUrl(data.getImageUrl());
            }
            Room updatedRoom=roomRepo.save(existingRoom);
            Map<String, Object> response=new HashMap<>();
            response.put("message", "Room updated successfully");
            response.put("room", updatedRoom);
            return ResponseEntity.ok(response);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while updating the room"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteRoom(Long id){
        try{
           
            Optional<Room> room=roomRepo.findById(id);
            if(room.isEmpty()){
                throw new CustomException("Room not found", HttpStatus.NOT_FOUND);
            }
            roomRepo.delete(room.get());
            Map<String, String> response=new HashMap<>();
            response.put("message", "Room deleted successfully");
            return ResponseEntity.ok(response);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while deleting the room"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    public ResponseEntity<?>changeStatusRoom(Long id){
        try{
            Optional<Room> room=roomRepo.findById(id);
            if(room.isEmpty()){
                throw new CustomException("Room not found", HttpStatus.NOT_FOUND);
            }
            Room updatedRoom=room.get();
            updatedRoom.setAvailable(!updatedRoom.getAvailable());
            Room savedRoom=roomRepo.save(updatedRoom);
            Map<String, Object> response=new HashMap<>();
            response.put("message", "Room status changed successfully");
            response.put("room", savedRoom);
            return ResponseEntity.ok(response);
        }
        catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred while changing the status of the room"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
