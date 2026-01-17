package hotel.example.hotelreservaion.dto;

import hotel.example.hotelreservaion.model.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserDto {
    @NotBlank(message = "nom is required")
    private String nom;
    @NotBlank(message = "prenom is required")
    private String prenom;
    @NotBlank(message = "email is required")
    @Email(message = "email should be valid")
    private String email;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "role is required")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
