package hotel.example.hotelreservaion.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer roomNumber;
    private String type;
    private Double price;
    private Boolean available;
    private String description;
    private String imageUrl;

    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL)
    private List<Booking> bookings;
    
}
