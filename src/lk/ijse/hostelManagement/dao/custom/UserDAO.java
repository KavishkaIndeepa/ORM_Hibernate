package lk.ijse.hostelManagement.dao.custom;

import lk.ijse.hostelManagement.dao.CrudDAO;
import lk.ijse.hostelManagement.entity.Login;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO extends CrudDAO<Login, String> {

    ArrayList<Login> getAllUsers() throws SQLException, ClassNotFoundException;
}
