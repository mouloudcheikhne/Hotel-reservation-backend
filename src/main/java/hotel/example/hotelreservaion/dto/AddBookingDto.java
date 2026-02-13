package hotel.example.hotelreservaion.dto;

import java.sql.Date;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddBookingDto {
    @NotNull(message = "user id is required")
    private Long userId;
    @NotNull(message = "room id is required")
    private Long roomId;
    @NotNull(message = "start date is required")
    private Date startDate;
    @NotNull(message = "end date is required")
    private Date endDate;
}
