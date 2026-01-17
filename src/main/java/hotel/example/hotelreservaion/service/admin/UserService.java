package hotel.example.hotelreservaion.service.admin;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hotel.example.hotelreservaion.dto.AddUserDto;
import hotel.example.hotelreservaion.model.User;
import hotel.example.hotelreservaion.repository.UserReposiory;

@Service
public class UserService {
    @Autowired
    private UserReposiory userReposiory;
    
    public ResponseEntity<?> getAllUsers(){
        try{
            List<User> users=userReposiory.findAll();
            return ResponseEntity.ok(users);
        }catch(Exception e){
            Map<String, String> errors=new HashMap<>();
            errors.put("message", "An error occurred while getting the users"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
        }
    }
    public ResponseEntity<?> addUser(AddUserDto data){
        try{
            Optional<User> foundUser=userReposiory.findByEmail(data.getEmail());
            if(foundUser.isPresent()){
                Map<String, String> errors=new HashMap<>();
                errors.put("message", "Email already in use");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }
            User newUser=User.builder().nom(data.getNom()).prenom(data.getPrenom()).email(data.getEmail()).password(data.getPassword()).role(data.getRole()).build();
            User savedUser=userReposiory.save(newUser);
            return ResponseEntity.ok(savedUser);
        }catch(Exception e){
            Map<String, String> errors=new HashMap<>();
            errors.put("message", "An error occurred while adding the user"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
        }
    }
}
