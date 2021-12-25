package service;

import ticketReservation.dao.PassengerDao;
import ticketReservation.entity.Passenger;

import java.util.List;

public class PassengerService {
    PassengerDao passengerDao = new PassengerDao();

    public void savePassenger(Passenger passenger){
        passengerDao.save(passenger);
    }

    public Passenger findPassengerByUserAndPass(String user,String pass){
        return passengerDao.findPassengerByUserAndPass(user,pass);
    }

    public boolean duplicatePassword(String pass) {
        List<Passenger> passengers = passengerDao.findAll();
        if (passengers.size()==0)
            return false;
        else if (passengers.stream().anyMatch(user -> user.getPassWord().equals(pass)))
            return true;
        return false;
    }

    public void updatePassenger(Passenger passenger){
        passengerDao.update(passenger);
    }
}
