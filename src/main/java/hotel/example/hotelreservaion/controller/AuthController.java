package hotel.example.hotelreservaion.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class AuthController {
    @GetMapping("/test")
    public String getMethodName() {
        return new String("hello project started");
    }
    
    
}
