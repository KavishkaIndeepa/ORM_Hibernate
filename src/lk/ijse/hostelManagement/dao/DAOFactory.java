package lk.ijse.hostelManagement.dao;

import lk.ijse.hostelManagement.dao.custom.impl.ReservationDAOImpl;
import lk.ijse.hostelManagement.dao.custom.impl.RoomDAOImpl;
import lk.ijse.hostelManagement.dao.custom.impl.StudentDAOImpl;
import lk.ijse.hostelManagement.dao.custom.impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case STUDENT:
                return new StudentDAOImpl();
            case ROOM:
                return new RoomDAOImpl();
            case RESERVATION:
                return new ReservationDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes {
        STUDENT, ROOM, RESERVATION,USER
    }
}
