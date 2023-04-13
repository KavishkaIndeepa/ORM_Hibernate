package lk.ijse.hostelManagement.bo.custom;

import lk.ijse.hostelManagement.bo.SuperBO;
import lk.ijse.hostelManagement.dto.ReservationDto;
import lk.ijse.hostelManagement.dto.RoomDto;
import lk.ijse.hostelManagement.dto.StudentDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationBO extends SuperBO {
    ArrayList<ReservationDto> getAllReserve() throws SQLException, ClassNotFoundException;

    ArrayList<ReservationDto> getAllReserveSearch(String id) throws SQLException, ClassNotFoundException;

    boolean updateReserve(ReservationDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteReserve(String id) throws SQLException, ClassNotFoundException;

    String generateNewId() throws SQLException, ClassNotFoundException;

    boolean existReserveID(String id) throws SQLException, ClassNotFoundException;

    boolean existStudent(String id) throws SQLException, ClassNotFoundException;

    boolean PurchaseRoom(ReservationDto dto) throws SQLException, ClassNotFoundException;

    StudentDto searchStudent(String id) throws SQLException, ClassNotFoundException;

    RoomDto searchRoom(String code) throws SQLException, ClassNotFoundException;

    boolean checkRoomIsAvailable(String code) throws SQLException, ClassNotFoundException;

    boolean checkStudentIsAvailable(String id) throws SQLException, ClassNotFoundException;

    String generateNewReserveID() throws SQLException, ClassNotFoundException;

    ArrayList<StudentDto> getAllStudents() throws SQLException, ClassNotFoundException;

    ArrayList<RoomDto> getAllRooms() throws SQLException, ClassNotFoundException;

}
