package hotel.example.hotelreservaion.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hotel.example.hotelreservaion.dto.LoginDto;
import hotel.example.hotelreservaion.dto.RegesterDto;
import hotel.example.hotelreservaion.dto.ResponceLoginDto;
import hotel.example.hotelreservaion.dto.ResponceRegesterDtO;
import hotel.example.hotelreservaion.model.CustomUserDetails;
import hotel.example.hotelreservaion.model.User;
import hotel.example.hotelreservaion.model.UserRole;
import hotel.example.hotelreservaion.repository.UserReposiory;
import hotel.example.hotelreservaion.util.JwtUtil;

@Service
public class AuthService {

   

    @Autowired
    private UserReposiory userReposiory;

   @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    public ResponseEntity<?> register(RegesterDto data){
        try{
  Optional<User> foundUser=userReposiory.findByEmail(data.getEmail());
        Map<String, String> errors=new HashMap<>(); 
        if(foundUser.isPresent()){
            errors.put("message", "Email already in use");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        User newUser=User.builder().nom(data.getNom()).email(data.getEmail()).prenom(data.getPrenom()).password(passwordEncoder.encode(data.getPassword())).role(UserRole.ADMIN).build();
        userReposiory.save(newUser);
        ResponceRegesterDtO responceResgister= ResponceRegesterDtO.builder().email(newUser.getEmail()).nom(newUser.getNom()).prenom(newUser.getPrenom()).role(UserRole.USER.name()).build();
        return ResponseEntity.ok(responceResgister);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration"+e.getMessage());

        }
      
    }
    public ResponseEntity<?> login(LoginDto data){
        try{
            authManager.authenticate(new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword()));
            CustomUserDetails userDetails = myUserDetailsService.loadUserByUsername(data.getEmail());
            String role=userDetails.getAuthorities().iterator().next().getAuthority();
            String token=jwtUtil.generateToken(data.getEmail(), role);
            ResponceLoginDto responceLogin= ResponceLoginDto.builder().email(data.getEmail()).token(token).prenom(userDetails.getPrenom()).role(role).build();

            return ResponseEntity.ok( responceLogin);
        }catch(Exception e){
            Map<String, String> errors=new HashMap<>();
            errors.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
        }
    }
    
}
