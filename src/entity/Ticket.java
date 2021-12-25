package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Ticket {//public class Ticket implements Cloneable

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String origin;
    @Column(nullable = false)
    private String destination;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date departureDate;

    private LocalTime departureTime;

    private double cost;

    @ManyToOne
    private Bus bus;
    @OneToMany(mappedBy = "ticket")
    private List<BookingTicket> bookingTicket=new ArrayList<>();

    public Ticket(String origin, String destination, Date departureDate, LocalTime departureTime, double cost, Bus bus) {
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.cost = cost;
        this.bus = bus;
    }

    @Override
    public String toString() {
        return "Ticket " + id +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", departureDate=" + departureDate +
                ", departureTime=" + departureTime +
                ", availableSeat="  +bus.getAvailableSeat()+
                ", busType= " + bus.getType() + '\'' +
                ", companyName= " + bus.getCompany().getCompanyName() + '\'' +
                ", cost=" + cost +
                '}';
    }

}