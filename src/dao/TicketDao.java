package dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import ticketReservation.dto.TicketDto;
import ticketReservation.entity.Ticket;
import ticketReservation.enumration.State;
import ticketReservation.util.HibernateUtil;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class TicketDao {
    private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    public void save(Ticket ticket) {//insert
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(ticket);
        transaction.commit();
        session.close();
    }


    public Ticket findById(int ticketId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("From Ticket t Where t.id=:id");
        query.setParameter("id", ticketId);
        Ticket ticket = (Ticket) query.uniqueResult();
        transaction.commit();
        session.close();
        return ticket;
    }
    //which one????????????????
  /*  public Ticket getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Ticket ticket = session.get(Ticket.class, id);
        transaction.commit();
        session.close();
        return ticket;
    }*/

    public List<Ticket> findForAdminReport() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query<Ticket> query = session.createQuery("FROM Ticket t join fetch bus b where  where  b.state =:state");
        query.setParameter("state", State.AVAILABLE);
        List<Ticket> ticketList = query.list();
        transaction.commit();
        session.close();
        return ticketList;
    }

    public List<TicketDto> findTicket(int start, int pageSize, String origin, String destination, Date date,
                                      String busType, String companyName, double startPrice, double endPrice, LocalTime startTime, LocalTime endTime) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Criteria criteria = session.createCriteria(Ticket.class, "t");
        criteria.createAlias("t.bus", "b");
        criteria.createAlias("b.company", "c");
        criteria.setFirstResult(start);
        criteria.setMaxResults(pageSize);
        SimpleExpression originCond = Restrictions.eq("t.origin", origin);
        SimpleExpression destinationCond = Restrictions.eq("t.destination", destination);
        LogicalExpression and = Restrictions.and(originCond, destinationCond);
        criteria.add(and);
        if (date != null) {
            SimpleExpression eq = Restrictions.eq("t.departureDate", date);
            criteria.add(eq);
        }
        if (busType != null) {
            SimpleExpression b = Restrictions.eq("b.type", busType);
            criteria.add(b);
        }
        if (companyName != null) {
            SimpleExpression c = Restrictions.eq("c.companyName", companyName);
            criteria.add(c);
        }
        if (startPrice != 0 && endPrice != 0) {
            Criterion between = Restrictions.between("t.cost", startPrice, endPrice);
            criteria.add(between);
        }
        if (startTime != null && endTime != null) {
            Criterion betweenTime = Restrictions.between("t.departureTime", startTime, endTime);
            criteria.add(betweenTime);
        }
        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("c.companyName").as("companyName"))
                .add(Projections.property("b.type").as("busType"))
                .add(Projections.property("t.id").as("id"))
                .add(Projections.property("t.departureTime").as("departureTime"))
                .add(Projections.property("t.cost").as("cost"))
                .add(Projections.property("b.availableSeat").as("availableSeat"))
                .add(Projections.property("t.departureDate").as("departureDate")));

        criteria.setResultTransformer(Transformers.aliasToBean(TicketDto.class));
        List<TicketDto> list = criteria.list();
        transaction.commit();
        session.close();
        return list;
    }


    public Long findTicketSize(String origin, String destination, Date date,
                               String busType, String companyName, double startPrice, double endPrice, LocalTime startTime, LocalTime endTime) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Criteria criteria = session.createCriteria(Ticket.class, "t");
        criteria.createAlias("t.bus", "b");
        criteria.createAlias("b.company", "c");
        SimpleExpression originCond = Restrictions.eq("t.origin", origin);
        SimpleExpression destinationCond = Restrictions.eq("t.destination", destination);
        LogicalExpression and = Restrictions.and(originCond, destinationCond);
        criteria.add(and);
        if (date != null) {
            SimpleExpression eq = Restrictions.eq("t.departureDate", date);
            criteria.add(eq);
        }
        if (busType != null) {
            SimpleExpression b = Restrictions.eq("b.type", busType);
            criteria.add(b);
        }
        if (companyName != null) {
            SimpleExpression c = Restrictions.eq("c.companyName", companyName);
            criteria.add(c);
        }
        if (startPrice != 0 && endPrice != 0) {
            Criterion between = Restrictions.between("t.cost", startPrice, endPrice);
            criteria.add(between);
        }
        if (startTime != null && endTime != null) {
            Criterion betweenTime = Restrictions.between("t.departureTime", startTime, endTime);
            criteria.add(betweenTime);
        }

        long count = criteria.list().stream().count();
        transaction.commit();
        session.close();
        return count;
    }

    public void update(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(ticket);
        transaction.commit();
        session.close();
    }

    public boolean delete(int ticketId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query<Ticket> query = session.createQuery("delete from Ticket t where t.id= :id");
        Query<Ticket> id = query.setParameter("id", ticketId);//todo cast to int
        transaction.commit();
        session.close();
        /*if (id > 0) {?????????????????
            return true;
        }*/
        return true;
    }

}