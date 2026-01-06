package hotel.example.hotelreservaion.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class UserController {
    @GetMapping("/")
    public String test() {
        return new String("server is running ");
    }
    
    @GetMapping("/api/user/me")
    public ResponseEntity<?> getMethodName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(auth.getPrincipal());
    }
    
}
