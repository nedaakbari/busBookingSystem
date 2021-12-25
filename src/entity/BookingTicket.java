package entity;

import lombok.Data;
import ticketReservation.util.StringListConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class BookingTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Convert(converter = StringListConverter.class)
    private List<String> seatSate = new ArrayList<>();//شماره صندلی

    private double totalCost;

    @ManyToOne
    private Passenger passenger;
    @ManyToOne
    private Ticket ticket;

    @Override
    public String toString() {
        return "Ticket " + id +
        " => count: " +seatSate.size()+
                ", seatSate=" + seatSate +
                ", totalCost=" + totalCost +
                  passenger.print() +
                ", Origin=" + ticket.getOrigin() +
                ", Destination=" + ticket.getDestination() +
                ", DepartureDate=" + ticket.getDepartureDate() +
                ", DepartureTime=" + ticket.getDepartureTime() +
                '}';
    }
}
