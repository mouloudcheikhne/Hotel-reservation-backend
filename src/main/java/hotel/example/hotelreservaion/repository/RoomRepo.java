package hotel.example.hotelreservaion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hotel.example.hotelreservaion.model.Room;

public interface RoomRepo  extends JpaRepository<Room,Long>{

    boolean existsByRoomNumber(Integer roomNumber);

    Optional<Room> findByRoomNumber(String roomNumber);

} 