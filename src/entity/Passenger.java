package entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ticketReservation.enumration.Gender;
import ticketReservation.enumration.Role;
import ticketReservation.util.StringListConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = {"nationalCode"})
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column( length = 50)
    private String firstName;
    @Column( length = 50)
    private String lastName;
    private String userName;
    private String passWord;
    private String nationalCode;

    //@Pattern(regexp="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", message="Please enter valid Email")
    // @ElementCollection(targetClass = String.class)
    @Convert(converter = StringListConverter.class)
    private List<String> phoneNumbers = new ArrayList<>();

    @OneToMany(mappedBy = "passenger",fetch = FetchType.EAGER)
    private List<BookingTicket> ticketList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;
    @CreationTimestamp
    private Date registerDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String email;

    public Passenger(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    @Builder(setterPrefix = "with")
    public Passenger(String firstName, String lastName, String nationalCode, List<String> phoneNumbers, Gender gender, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.phoneNumbers = phoneNumbers;
        this.gender = gender;
        this.role = Role.PASSENGER;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                ", role=" + role +
                ", registerDate=" + registerDate +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                '}';
    }

    public String print() {
        return "Passenger: " +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                ", gender=" + gender ;
    }
}

