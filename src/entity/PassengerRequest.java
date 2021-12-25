package entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Builder(setterPrefix = "with")
@Data
public class PassengerRequest {
    private String origin;
    private String destination;
    private Date date;
    private String busType;
    private String companyName;
    private double startPrice;
    private double endPrice;
    private LocalTime startTime;
    private LocalTime endTime;
}
