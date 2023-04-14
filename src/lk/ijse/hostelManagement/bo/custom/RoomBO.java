package lk.ijse.hostelManagement.bo.custom;

import lk.ijse.hostelManagement.bo.SuperBO;
import lk.ijse.hostelManagement.dto.RoomDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RoomBO extends SuperBO {
    ArrayList<RoomDto> getAllRooms() throws SQLException, ClassNotFoundException;

    ArrayList<RoomDto> searchAllRooms(String id) throws SQLException, ClassNotFoundException;

    boolean saveRooms(RoomDto dto) throws SQLException, ClassNotFoundException;

    boolean updateRooms(RoomDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteRooms(String id) throws SQLException, ClassNotFoundException;

    boolean existRoomsID(String id) throws SQLException, ClassNotFoundException;
}
