package dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class TicketDto {
    private int id;
    private String companyName;
    private String busType;//todo BusType
    private LocalTime departureTime;
    private Date departureDate;
    private double cost;
    private int availableSeat;

    @Override
    public String toString() {
        return "Ticket " + id +
                ", companyName=" + companyName +
                ", busType='" + busType + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", cost=" + cost +
                ", availableSeat=" + availableSeat +
                '}';
    }

}
