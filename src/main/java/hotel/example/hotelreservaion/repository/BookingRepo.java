package hotel.example.hotelreservaion.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hotel.example.hotelreservaion.model.Booking;
import hotel.example.hotelreservaion.model.BookingStatus;

public interface BookingRepo  extends JpaRepository<Booking,Long>{
    
    List<Booking> findByStatusAndEndDateAfter(BookingStatus status, Date date);
    
}
