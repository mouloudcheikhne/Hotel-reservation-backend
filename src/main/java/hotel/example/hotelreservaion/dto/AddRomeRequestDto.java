package hotel.example.hotelreservaion.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddRomeRequestDto {
    @NotBlank(message = "room number is required")
    private Integer roomNumber;
    @NotBlank(message = "type is required")
    private String type;
    @NotBlank(message = "price is required")
    private Double price;
    private Boolean available;
    @NotBlank(message = "description is required")
    private String description;
    @NotBlank(message = "image url is required")
    private String imageUrl;
    
}
