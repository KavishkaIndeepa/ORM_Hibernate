package lk.ijse.hostelManagement.bo.custom;

import lk.ijse.hostelManagement.bo.SuperBO;
import lk.ijse.hostelManagement.dto.LoginDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    ArrayList<LoginDto> getAllUsers() throws SQLException, ClassNotFoundException;

    ArrayList<LoginDto> searchAllUser(String id) throws SQLException, ClassNotFoundException;

    boolean saveUser(LoginDto dto) throws SQLException, ClassNotFoundException;

    boolean updateUser(LoginDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteUser(String id) throws SQLException, ClassNotFoundException;

    boolean existUser(String id) throws SQLException, ClassNotFoundException;
}
