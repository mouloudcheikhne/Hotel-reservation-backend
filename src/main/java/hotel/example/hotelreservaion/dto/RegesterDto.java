package hotel.example.hotelreservaion.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class RegesterDto {
    @NotBlank(message = "nom required")
    private String nom;
     @NotBlank(message = "nom required")
    private String prenom;
     @NotBlank(message = "nom required")
     @Email(message = "email not valide")
    private String email;
     @NotBlank(message = "nom required")
    private String password;
    
}
