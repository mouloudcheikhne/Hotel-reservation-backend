package hotel.example.hotelreservaion.controller.admin;

import hotel.example.hotelreservaion.dto.AddRomeRequestDto;
import hotel.example.hotelreservaion.dto.UpdateRomeDto;
import hotel.example.hotelreservaion.service.admin.RoomService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    private static final String UPLOAD_DIR = "uploads/rooms";

    @PostMapping("/add")
    public ResponseEntity<?> addRoom(
            @RequestParam(value = "roomNumber", required = true) Integer roomNumber,
            @RequestParam(value = "description", required = true) String description,
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "price", required = true) Double price,
            @RequestParam(value = "image", required = true) MultipartFile image) {

        try {
            // 🔎 validations
            if (image == null || image.isEmpty()) {
                return ResponseEntity.badRequest().body("Image is required");
            }
            if (roomNumber == null || roomNumber <= 0) {
                return ResponseEntity.badRequest().body("Room number is required and must be positive");
            }
            if (description == null || description.isBlank()) {
                return ResponseEntity.badRequest().body("Description is required");
            }
            if (type == null || type.isBlank()) {
                return ResponseEntity.badRequest().body("Type is required");
            }
            if (price == null || price <= 0) {
                return ResponseEntity.badRequest().body("Price must be positive");
            }

          
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Files.createDirectories(uploadPath);

        
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath);

           
            String imageUrl = "/uploads/rooms/" + fileName;

            AddRomeRequestDto data = AddRomeRequestDto.builder()
                    .roomNumber(roomNumber)
                    .description(description)
                    .type(type)
                    .price(price)
                    .imageUrl(imageUrl)
                    .build();

            return roomService.addRoom(data);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error while adding room: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long id,@RequestParam(value = "roomNumber", required = false) Integer roomNumber,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "image", required = false) MultipartFile image){
                try
                {
                    UpdateRomeDto data=new UpdateRomeDto();
                   if(roomNumber!=null){
                    data.setRoomNumber(roomNumber);
                   }
                   if(description!=null){
                    data.setDescription(description);
                   }
                   if(type!=null){
                    data.setType(type);
                   }
                   if(price!=null){
                    data.setPrice(price);
                   }
                   if(image!=null){
                    Path uploadPath = Paths.get(UPLOAD_DIR);
                    Files.createDirectories(uploadPath);

        
                    String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(image.getInputStream(), filePath);

                    String imageUrl = "/uploads/rooms/" + fileName;
                    data.setImageUrl(imageUrl);
                   }
                   return roomService.updateRoom(data,id);
                }
        catch(Exception e){
            Map<String, String> error=new HashMap<>();
            error.put("message", "Error while updating room: "+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id){
        return roomService.deleteRoom(id);
    }
    @GetMapping("")
    public ResponseEntity<?> getAllRooms(){
        return roomService.getAllRooms();
    }
    @PutMapping("/change-status/{id}")
    public ResponseEntity<?> changeStatusRoom(@PathVariable Long id){
        return roomService.changeStatusRoom(id);
    }
}
