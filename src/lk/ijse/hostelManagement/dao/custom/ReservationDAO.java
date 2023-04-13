package lk.ijse.hostelManagement.dao.custom;

import lk.ijse.hostelManagement.dao.CrudDAO;
import lk.ijse.hostelManagement.entity.Reservation;

import java.sql.SQLException;

public interface ReservationDAO extends CrudDAO<Reservation, String> {
    String generateNewId() throws SQLException, ClassNotFoundException;

    boolean existStudent(String id) throws SQLException, ClassNotFoundException;
}
