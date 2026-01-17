package hotel.example.hotelreservaion.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Room room;
    private Date startDate;
    private Date endDate;
    private double totalPrice;
     @Enumerated(EnumType.STRING)  
    private BookingStatus status;
   

    
}
