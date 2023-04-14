package lk.ijse.hostelManagement.bo.custom.impl;

import lk.ijse.hostelManagement.bo.custom.RoomBO;
import lk.ijse.hostelManagement.dao.DAOFactory;
import lk.ijse.hostelManagement.dao.custom.RoomDAO;
import lk.ijse.hostelManagement.dto.RoomDto;
import lk.ijse.hostelManagement.entity.Room;

import java.sql.SQLException;
import java.util.ArrayList;

public class RoomBOImpl implements RoomBO {

    private final RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    @Override
    public ArrayList<RoomDto> getAllRooms() throws SQLException, ClassNotFoundException {
        ArrayList<Room> all = roomDAO.getAll();
        ArrayList<RoomDto> allRoom = new ArrayList<>();
        for (Room room : all) {
            allRoom.add(new RoomDto(room.getRoomId(), room.getType(), room.getKeyMoney(), room.getQty()));
        }
        return allRoom;
    }

    @Override
    public ArrayList<RoomDto> searchAllRooms(String id) throws SQLException, ClassNotFoundException {
        Room all = roomDAO.search(id);
        ArrayList<RoomDto> allSRoom = new ArrayList<>();

        allSRoom.add(new RoomDto(all.getRoomId(), all.getType(), all.getKeyMoney(), all.getQty()));

        return allSRoom;
    }

    @Override
    public boolean saveRooms(RoomDto dto) throws SQLException, ClassNotFoundException {
        return roomDAO.save(new Room(dto.getRoomTypeId(), dto.getType(), dto.getKeyMoney(), dto.getQty()));
    }

    @Override
    public boolean updateRooms(RoomDto dto) throws SQLException, ClassNotFoundException {
        return roomDAO.update(new Room(dto.getRoomTypeId(), dto.getType(), dto.getKeyMoney(), dto.getQty()));

    }

    @Override
    public boolean deleteRooms(String id) throws SQLException, ClassNotFoundException {
        return roomDAO.delete(id);
    }

    @Override
    public boolean existRoomsID(String id) throws SQLException, ClassNotFoundException {
        return roomDAO.exist(id);
    }
}
