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
import hotel.example.hotelreservaion.exception.CustomException;
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
        if(foundUser.isPresent()){
            throw new CustomException("Email already in use", HttpStatus.BAD_REQUEST);
        }
        User newUser=User.builder().nom(data.getNom()).email(data.getEmail()).prenom(data.getPrenom()).password(passwordEncoder.encode(data.getPassword())).role(UserRole.USER).build();
        userReposiory.save(newUser);
        ResponceRegesterDtO responceResgister= ResponceRegesterDtO.builder().email(newUser.getEmail()).nom(newUser.getNom()).prenom(newUser.getPrenom()).role(UserRole.USER.name()).build();
        return ResponseEntity.ok(responceResgister);
        }catch(CustomException e){
            throw e;
        }
        catch(Exception e){
            throw new CustomException("An error occurred during registration"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
      
    }
    public ResponseEntity<?> login(LoginDto data){
        try{
            authManager.authenticate(new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword()));
            CustomUserDetails userDetails = myUserDetailsService.loadUserByUsername(data.getEmail());
            String role=userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
            String token=jwtUtil.generateToken(data.getEmail(), role);
            ResponceLoginDto responceLogin= ResponceLoginDto.builder().email(data.getEmail()).nom(userDetails.getNom()).token(token).prenom(userDetails.getPrenom()).role(role).build();

            return ResponseEntity.ok( responceLogin);
        }catch(CustomException e){
            throw e;
        }catch(Exception e){
            throw new CustomException("Invalid email or password", HttpStatus.BAD_REQUEST);
        }
    }
    
}
