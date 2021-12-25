package dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.transform.Transformers;
import ticketReservation.dto.ReportDto;
import ticketReservation.entity.Bus;
import ticketReservation.util.HibernateUtil;

import java.util.List;

public class BusDao {
    private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    public void save(Bus bus) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(bus);
        transaction.commit();
        session.close();
    }

    public void update(Bus bus) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(bus);
        transaction.commit();
        session.close();
    }

    public List<ReportDto> report(String busType) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Criteria criteria = session.createCriteria(Bus.class, "b");
        criteria.createAlias("b.company", "c");
        criteria.createAlias("b.ticketList", "t");
        SimpleExpression typeCond = Restrictions.eq("b.type", busType);
        criteria.add(typeCond);
        criteria.addOrder(Order.asc("t.departureDate"));

        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("c.companyName").as("companyName"))//=>جلوی asدقیقا نیمی باشه که برای دیتی او میذاریم
                .add(Projections.property("b.type").as("busType"))
                .add(Projections.property("b.plaque").as("plaque"))
                .add(Projections.property("b.type").as("busType"))
                .add(Projections.property("t.departureDate").as("departureDate"))
                .add(Projections.property("t.departureTime").as("departureTime"))
                .add(Projections.property("b.availableSeat").as("availableSeat")));

        criteria.setResultTransformer(Transformers.aliasToBean(ReportDto.class));
        List<ReportDto> list = criteria.list();
        transaction.commit();
        session.close();
        return list;
    }

}

