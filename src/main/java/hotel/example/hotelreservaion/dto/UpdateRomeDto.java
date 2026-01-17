package hotel.example.hotelreservaion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRomeDto {
    private Integer roomNumber;
    private String description;
    private String type;
    private Double price;
    private String imageUrl;
    
}
