package hotel.example.hotelreservaion.dto;

import hotel.example.hotelreservaion.model.BookingStatus;
import hotel.example.hotelreservaion.validation.ValidBookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeStatusBookingDto {
    @NotNull(message = "status is required")
    @ValidBookingStatus(message = "Invalid status. Valid statuses are: PENDING, CONFIRMED, CANCELLED")
    private BookingStatus status;
    
}
