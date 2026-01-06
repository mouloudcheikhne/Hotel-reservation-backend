package hotel.example.hotelreservaion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponceLoginDto {
    private String token;
    private String nom;
    private String prenom;
    private String email;
    private String role;
    
}
