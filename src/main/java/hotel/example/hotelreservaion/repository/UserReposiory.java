package hotel.example.hotelreservaion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hotel.example.hotelreservaion.model.User;

public interface UserReposiory extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    
}