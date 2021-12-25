package dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class ReportDto {
    private String busType;
    private String companyName;
    private int availableSeat;
    private Date departureDate;
    private LocalTime departureTime;
    private String plaque;

    @Override
    public String toString() {
        return "Bus By plaque: " + plaque +
                ", companyName= " + companyName + '\'' +
                ", availableSeat= " + availableSeat +
                ", soldTicket= " + (16 - availableSeat) +
                ", departureDate= " + departureDate +
                ", departureTime= " + departureTime + '\'' +

                '}';
    }
}
