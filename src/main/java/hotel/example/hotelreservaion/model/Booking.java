package hotel.example.hotelreservaion.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private Room room;
    private Date startDate;
    private Date endDate;
    private double totalPrice;
     @Enumerated(EnumType.STRING)  
    private BookingStatus status;
   

    
}
