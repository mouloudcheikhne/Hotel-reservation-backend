package hotel.example.hotelreservaion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hotel.example.hotelreservaion.model.CustomUserDetails;
import hotel.example.hotelreservaion.model.User;
import hotel.example.hotelreservaion.repository.UserReposiory;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired private UserReposiory userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(
            user.getNom(),
            user.getPrenom(),
            user.getEmail(),
            user.getPassword(),
            user.getRole()
    );
    }
}