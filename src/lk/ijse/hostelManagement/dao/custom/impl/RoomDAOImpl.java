package lk.ijse.hostelManagement.dao.custom.impl;

import lk.ijse.hostelManagement.dao.custom.RoomDAO;
import lk.ijse.hostelManagement.entity.Room;
import lk.ijse.hostelManagement.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public ArrayList<Room> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Room> allRoom;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Room");
        allRoom = (ArrayList<Room>) query.list();
        transaction.commit();
        session.close();
        return allRoom;
    }

    @Override
    public boolean save(Room room) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(room);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Room room) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(room);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Room room = session.get(Room.class, id);
        session.delete(room);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Room search(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Room room = session.get(Room.class, id);
        transaction.commit();
        session.close();
        return room;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("SELECT roomId FROM Room WHERE roomId=:id");
        String id1 = (String) query.setParameter("id", id).uniqueResult();
        if (id1 != null) {
            return true;
        }
        transaction.commit();
        session.close();
        return false;
    }
}
