package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ticketReservation.entity.Company;
import ticketReservation.util.HibernateUtil;

public class CompanyDao {

    private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    public void save(Company company) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(company);
        transaction.commit();
        session.close();
    }

    public Company findByName(String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<Company> query = session.createQuery("FROM Company c where c.companyName=:name", Company.class);
        query.setParameter("name", name);
        Company company = query.uniqueResult();
        transaction.commit();
        session.close();
        return company;
    }
}
