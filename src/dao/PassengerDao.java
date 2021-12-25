package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ticketReservation.entity.Passenger;
import ticketReservation.util.HibernateUtil;

import java.util.List;

public class PassengerDao {
    private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    public void save(Passenger user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    public void update(Passenger passenger) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(passenger);
        transaction.commit();
        session.close();
    }

    public Passenger findPassengerByUserAndPass(String user, String pass) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("From Passenger p Where p.userName = :user AND  p.passWord = :pass");
        query.setParameter("user", user);
        query.setParameter("pass", pass);
        Passenger passenger = (Passenger) query.uniqueResult();
        transaction.commit();
        session.close();
        return passenger;
    }

    public Passenger searchByNationalCode(String nationalCode) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("From Passenger p Where p.nationalCode = :nationalCode ");
        query.setParameter("nationalCode", nationalCode);
        Passenger passenger = (Passenger) query.uniqueResult();
        transaction.commit();
        session.close();
        return passenger;
    }

    public List<Passenger> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Passenger ");
        List<Passenger> users = query.list();
        transaction.commit();
        session.close();
        return users;
    }

}

