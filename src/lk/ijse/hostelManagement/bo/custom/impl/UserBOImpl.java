package lk.ijse.hostelManagement.bo.custom.impl;

import lk.ijse.hostelManagement.bo.custom.UserBO;
import lk.ijse.hostelManagement.dao.DAOFactory;
import lk.ijse.hostelManagement.dao.custom.UserDAO;
import lk.ijse.hostelManagement.dto.LoginDto;
import lk.ijse.hostelManagement.entity.Login;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public ArrayList<LoginDto> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<Login> all = userDAO.getAllUsers();
        ArrayList<LoginDto> allUSer = new ArrayList<>();
        for (Login users : all) {
            allUSer.add(new LoginDto(users.getUserID(), users.getName(), users.getAddress(), users.getContact_no(), users.getPassword(), users.getGender()));
        }
        return allUSer;
    }

    @Override
    public ArrayList<LoginDto> searchAllUser(String id) throws SQLException, ClassNotFoundException {
        Login all = userDAO.search(id);
        ArrayList<LoginDto> allUsersSearch = new ArrayList<>();
        allUsersSearch.add(new LoginDto(all.getUserID(), all.getName(), all.getAddress(), all.getContact_no(), all.getPassword(), all.getGender()));
        return allUsersSearch;
    }

    @Override
    public boolean saveUser(LoginDto dto) throws SQLException, ClassNotFoundException {
        return userDAO.save(new Login(dto.getUserID(), dto.getName(), dto.getAddress(), dto.getContact_no(), dto.getPassword(), dto.getGender()));
    }

    @Override
    public boolean updateUser(LoginDto dto) throws SQLException, ClassNotFoundException {
        return userDAO.update(new Login(dto.getUserID(), dto.getName(), dto.getAddress(), dto.getContact_no(), dto.getPassword(), dto.getGender()));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return userDAO.delete(id);
    }

    @Override
    public boolean existUser(String id) throws SQLException, ClassNotFoundException {
        return userDAO.exist(id);
    }
}
