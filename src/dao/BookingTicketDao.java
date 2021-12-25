package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ticketReservation.entity.BookingTicket;
import ticketReservation.util.HibernateUtil;

import java.util.List;

public class BookingTicketDao {
    private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    public void save(BookingTicket bookingTicket) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(bookingTicket);
        transaction.commit();
        session.close();
    }

    public List<BookingTicket> findTicketByPassengerId(int passengerId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<BookingTicket> query = session.createQuery("FROM BookingTicket b join fetch b.passenger p where  p.id=:passengerId");
        query.setParameter("passengerId", passengerId);
        List<BookingTicket> BookingTicketList = query.list();
        transaction.commit();
        session.close();
        return BookingTicketList;
    }
}
