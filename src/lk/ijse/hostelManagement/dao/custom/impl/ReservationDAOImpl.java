package lk.ijse.hostelManagement.dao.custom.impl;

import lk.ijse.hostelManagement.dao.custom.ReservationDAO;
import lk.ijse.hostelManagement.entity.Reservation;
import lk.ijse.hostelManagement.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationDAOImpl implements ReservationDAO {
    @Override
    public ArrayList<Reservation> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Reservation> allReserve = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("From Reservation");
        allReserve =(ArrayList<Reservation>) query.list();
        transaction.commit();
        session.close();
        return allReserve;
    }

    @Override
    public boolean save(Reservation reservation) throws SQLException, ClassNotFoundException {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        session.save(reservation);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Reservation reservation) throws SQLException, ClassNotFoundException {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        session.update(reservation);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        Reservation reservation=session.get(Reservation.class, id);
        session.delete(reservation);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Reservation search(String id) throws SQLException, ClassNotFoundException {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        Reservation reservation=session.get(Reservation.class, id);
        transaction.commit();
        session.close();
        return reservation;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("SELECT resId FROM Reservation WHERE resId=:id");
        String id1 = (String) query.setParameter("id", id).uniqueResult();
        if (id1 != null){
            return true;
        }
        transaction.commit();
        session.close();
        return false;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
       Session session=FactoryConfiguration.getInstance().getSession();
       Transaction transaction=session.beginTransaction();
       Query query=session.createSQLQuery("SELECT resId FROM Reservation ORDER BY resId DESC LIMIT 1");
       String s = (String) query.uniqueResult();
       transaction.commit();
       session.close();
       if(s != null){
           int newStudentId = Integer.parseInt(s.replace("REG-",""))+1;
           return String.format("REG-%03d", newStudentId);
       }
       return "REG-001";
    }

    @Override
    public boolean existStudent(String id) throws SQLException, ClassNotFoundException {
        Session session=FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        Query query=session.createQuery("SELECT studentId FROM Reservation WHERE studentId=:id");
        String id1=(String) query.setParameter("id", id).uniqueResult();
        if(id1 != null){
            return true;
        }
        transaction.commit();
        session.close();
        return false;
    }


}
