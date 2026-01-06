package hotel.example.hotelreservaion.controller;

import org.springframework.web.bind.annotation.RestController;

import hotel.example.hotelreservaion.dto.LoginDto;
import hotel.example.hotelreservaion.dto.RegesterDto;
import hotel.example.hotelreservaion.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @GetMapping("/test")
    public String getMethodName() {
        return new String("hello project started mouloud test");
    }
    @PostMapping("/register")
    public ResponseEntity<?> Register(@Valid @RequestBody RegesterDto data) {
        
        return authService.register(data);
    }
    @PostMapping("/login")
    public ResponseEntity<?> Login(@Valid @RequestBody LoginDto loginDto) { 
        return authService.login(loginDto);
    }

    
    
    
}
