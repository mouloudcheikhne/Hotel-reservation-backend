package hotel.example.hotelreservaion.controller.admin;

import org.springframework.web.bind.annotation.RestController;

import hotel.example.hotelreservaion.dto.AddUserDto;
import hotel.example.hotelreservaion.dto.UpdateUserDto;
import hotel.example.hotelreservaion.service.admin.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("/api/admin/users")
public class UserController {
    // @GetMapping("/")
    // public String test() {
    //     return new String("server is running ");
    // }
    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/me")
    public ResponseEntity<?> getMethodName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(auth.getPrincipal());
    }
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody AddUserDto data){
        return userService.addUser(data);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto data){
        return userService.updateUser(id, data);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
