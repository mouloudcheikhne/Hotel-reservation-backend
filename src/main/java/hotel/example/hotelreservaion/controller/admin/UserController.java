package hotel.example.hotelreservaion.controller.admin;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("/api/admin/users")
public class UserController {
    // @GetMapping("/")
    // public String test() {
    //     return new String("server is running ");
    // }
    
    @GetMapping("/me")
    public ResponseEntity<?> getMethodName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(auth.getPrincipal());
    }
    // @PostMapping("/add")
    // public ResponseEntity<?> addUser(@RequestBody AddUserDto entity) {
    //     //TODO: process POST request
        
    //     return entity;
    // }
    
    
}
