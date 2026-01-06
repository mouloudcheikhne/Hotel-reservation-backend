package hotel.example.hotelreservaion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponceRegesterDtO {
    private String nom;
    private String prenom;
    private String email;
    private String role;
    
    
}
