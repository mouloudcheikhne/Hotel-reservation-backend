package hotel.example.hotelreservaion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.example.hotelreservaion.service.PublicService;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/api/rooms")
public class PublicController {
    @Autowired
    private PublicService publicService;

    @GetMapping("")
    public ResponseEntity<?> getAllRooms(){
        return publicService.getallRooms();
    }
    @GetMapping("/dates-reserved/{roomId}")
    public ResponseEntity<?> getDatesReserved(@PathVariable Long roomId) {
        return publicService.getDatesReserved(roomId);
    }
    
  
    

    
}
