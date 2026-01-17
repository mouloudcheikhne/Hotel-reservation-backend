package hotel.example.hotelreservaion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.example.hotelreservaion.service.ReseptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/reseption")
public class ReceptionController {
    @Autowired
    private ReseptionService reseptionService;
    
    @GetMapping("/rooms-available")
    public ResponseEntity<?> getAllRoomAvailable() {
        return reseptionService.getAllRoomAvailable();
    }
    
}
