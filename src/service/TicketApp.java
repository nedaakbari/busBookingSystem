package service;

import ticketReservation.dao.BookingTicketDao;
import ticketReservation.dao.BusDao;
import ticketReservation.dao.CompanyDao;
import ticketReservation.dao.TicketDao;
import ticketReservation.dto.ReportDto;
import ticketReservation.dto.TicketDto;
import ticketReservation.entity.BookingTicket;
import ticketReservation.entity.Bus;
import ticketReservation.entity.Company;
import ticketReservation.entity.Ticket;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class TicketApp {
    CompanyDao companyDao = new CompanyDao();
    BusDao busDao = new BusDao();
    TicketDao ticketDao = new TicketDao();
    BookingTicketDao bookingTicketDao = new BookingTicketDao();


    public void saveCompany(Company company) {
        companyDao.save(company);
    }

    public void saveBus(Bus bus) {
        busDao.save(bus);
    }

    public void updateBus(Bus bus) {
        busDao.update(bus);
    }

    public void saveTicket(Ticket ticket) {
        ticketDao.save(ticket);
    }

    public List<TicketDto> search(int start, int pageSize, String origin, String destination, Date date,
                                  String busType, String companyName, double startPrice, double endPrice, LocalTime startTime, LocalTime endTime) {
        return ticketDao.findTicket(start, pageSize, origin, destination, date,
                busType, companyName, startPrice, endPrice, startTime, endTime);
    }

    public Long searchForSize(String origin, String destination, Date date,
                              String busType, String companyName, double startPrice, double endPrice, LocalTime startTime, LocalTime endTime) {
        return ticketDao.findTicketSize(origin, destination, date,
                busType, companyName, startPrice, endPrice, startTime, endTime);
    }

    public Ticket findTicketById(int id) {
        return ticketDao.findById(id);
    }

    public void saveBookingTicket(BookingTicket booking) {
        bookingTicketDao.save(booking);
    }

    public List<BookingTicket> findTicketByPassengerId(int passengerId) {
        return bookingTicketDao.findTicketByPassengerId(passengerId);
    }

    public Company findCompanyByName(String name) {
        return CompanyDao.findByName(name);
    }

    public List<ReportDto> findForAdminReport(String type) {
        return busDao.report(type);
    }


}
