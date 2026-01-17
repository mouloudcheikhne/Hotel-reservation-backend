package hotel.example.hotelreservaion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hotel.example.hotelreservaion.model.Booking;

public interface BookingRepo  extends JpaRepository<Booking,Long>{
    
}
