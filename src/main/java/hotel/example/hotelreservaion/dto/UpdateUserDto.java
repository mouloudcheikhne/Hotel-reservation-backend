package hotel.example.hotelreservaion.dto;

import hotel.example.hotelreservaion.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private UserRole role;
}
