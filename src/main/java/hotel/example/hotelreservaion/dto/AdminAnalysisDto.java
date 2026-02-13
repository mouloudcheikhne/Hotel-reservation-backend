package hotel.example.hotelreservaion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminAnalysisDto {
    private Integer totalUsers;
    private Integer totalRooms;
    private Integer reservedRooms;
    private Integer totalBookings;
}
