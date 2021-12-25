package entity;

import lombok.Data;
import ticketReservation.enumration.Role;

@Data

public class Manager {
    private String user;
    private String passWord;
    private Role role;

    public Manager() {
        this.user = "admin";
        this.passWord = "admin";
        this.role = Role.MANAGER;
    }
}
