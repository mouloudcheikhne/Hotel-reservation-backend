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
import hotel.example.hotelreservaion.model.Room;
import hotel.example.hotelreservaion.repository.RoomRepo;

@Service
public class RoomService {
    @Autowired
    private RoomRepo roomRepo;
    public ResponseEntity<?> getAllRooms(){
        try{
            List<Room> rooms=roomRepo.findAll();
            Map<String, Object> response=new HashMap<>();
            response.put("message", "Rooms fetched successfully");
            response.put("rooms", rooms);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            Map<String, String> errors=new HashMap<>();
            errors.put("message", "An error occurred while getting the rooms"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
        }
    }

    public ResponseEntity<?> addRoom(AddRomeRequestDto data){
        try{
            Map<String, String> errors=new HashMap<>();
            boolean exists=roomRepo.existsByRoomNumber(data.getRoomNumber());
            if(exists){
                errors.put("message", "Room already exists");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }
            Room newRoom=Room.builder().roomNumber(data.getRoomNumber()).type(data.getType()).price(data.getPrice()).available(false).description(data.getDescription()).imageUrl(data.getImageUrl()).build();
            Room savedRoom=roomRepo.save(newRoom);
            Map<String, Object> response=new HashMap<>();
            response.put("message", "Room added successfully");
            response.put("room", savedRoom);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the room"+e.getMessage());
        }
    }

    public ResponseEntity<?> updateRoom(UpdateRomeDto data,Long id){
        try{
            Map<String, String> errors=new HashMap<>();
            Optional<Room> room=roomRepo.findById(id);
            if(room.isEmpty()){
                errors.put("message", "Room not found");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }
            Room existingRoom=room.get();
            if(data.getRoomNumber()!=null){
                boolean exists=roomRepo.existsByRoomNumber(data.getRoomNumber());
                if(exists){
                    errors.put("message", "Room number already exists");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
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
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the room"+e.getMessage());
        }
    }

    public ResponseEntity<?> deleteRoom(Long id){
        try{
            Map<String, String> errors=new HashMap<>();
            Optional<Room> room=roomRepo.findById(id);
            if(room.isEmpty()){
                errors.put("message", "Room not found");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }
            roomRepo.delete(room.get());
            Map<String, Object> response=new HashMap<>();
            response.put("message", "Room deleted successfully");
            return ResponseEntity.ok(response);
        }
        catch(Exception e){
            Map<String, String> errors=new HashMap<>();
            errors.put("message", "An error occurred while deleting the room"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
        }
    }

}
