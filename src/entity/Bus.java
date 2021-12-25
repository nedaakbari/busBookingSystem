package entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ticketReservation.util.StringListConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = {"plaque"})
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String plaque;

    private int totalSeats;

    @Convert(converter = StringListConverter.class)
    private List<String> seatNumber = new ArrayList<>();

    @OneToOne
    private Company company;

    //@Enumerated(EnumType.STRING)
    private String type;
    //@Enumerated(EnumType.STRING)
    private String state;//full or available
    private int availableSeat;

    @OneToMany(mappedBy = "bus", fetch = FetchType.EAGER)
    private List<Ticket> ticketList = new ArrayList<>();//برای این با لیستی از تیکت ها رابطه گذاشتم چون که یک اتوبوس در روزهای مختلف سفر داره و مقصد و مبدا جز پروپرتی های اتوبوس نیس


    public Bus(String plaque, List<String> seatNumber, Company company, String type, int totalSeats) {
        this.plaque = plaque;
        this.totalSeats = totalSeats;
        this.seatNumber = seatNumber;
        this.company = company;
        this.type = type;
    }


    public static List<String> busRow(int numberOfRows) {
        List<String> seat = new ArrayList<>();
        String letters[] = {"A", "B", "C", "D"};
        if (numberOfRows <= 0) {
            System.out.println("Without Seats? It isn't a bus! please insert number of rows");
        }
        String s = "";
        for (int i = 1; i <= numberOfRows; i++) {
            for (int j = 0; j < letters.length; j++)
                s = i + "" + letters[j] + " ";
            seat.add(s);
        }
        return seat;
    }
}
