package hotel.example.hotelreservaion.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponceGetDatesReserved {
    private Date startDate;
    private Date endDate;
    
}
