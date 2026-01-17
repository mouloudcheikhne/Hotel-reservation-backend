package hotel.example.hotelreservaion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.example.hotelreservaion.service.ClientService;

@RestController
@RequestMapping("/api/rooms")
public class PublicController {
    @Autowired
    private ClientService clientService;
    @GetMapping("")
    public ResponseEntity<?> getAllRooms(){
        return clientService.getallRooms();
    }

    
}
