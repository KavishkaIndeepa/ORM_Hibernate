package lk.ijse.hostelManagement.bo.custom.impl;

import lk.ijse.hostelManagement.bo.custom.ReservationBO;
import lk.ijse.hostelManagement.dao.DAOFactory;
import lk.ijse.hostelManagement.dao.custom.ReservationDAO;
import lk.ijse.hostelManagement.dao.custom.RoomDAO;
import lk.ijse.hostelManagement.dao.custom.StudentDAO;
import lk.ijse.hostelManagement.dto.ReservationDto;
import lk.ijse.hostelManagement.dto.RoomDto;
import lk.ijse.hostelManagement.dto.StudentDto;
import lk.ijse.hostelManagement.entity.Reservation;
import lk.ijse.hostelManagement.entity.Room;
import lk.ijse.hostelManagement.entity.Student;
import lk.ijse.hostelManagement.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationBOImpl implements ReservationBO {

    private final ReservationDAO reserveDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    private final RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);

    @Override
    public ArrayList<ReservationDto> getAllReserve() throws SQLException, ClassNotFoundException {
        ArrayList<Reservation> all = reserveDAO.getAll();
        ArrayList<ReservationDto> allReserve=new ArrayList<>();
        for (Reservation r : all){
            allReserve.add(new ReservationDto(r.getResId(),r.getDate(),r.getStudentId().getStudentId(),r.getRoomId().getRoomId(),r.getKeyMoney(),r.getAdvance(),r.getStatues()));
        }
        return allReserve;
    }

    @Override
    public ArrayList<ReservationDto> getAllReserveSearch(String id) throws SQLException, ClassNotFoundException {
        Reservation all = reserveDAO.search(id);
        ArrayList<ReservationDto> allSearchReserve = new ArrayList<>();
        allSearchReserve.add(new ReservationDto(all.getResId(),all.getDate(),all.getStudentId().getStudentId(),all.getRoomId().getRoomId(),all.getKeyMoney(),all.getAdvance(),all.getStatues()));
        return allSearchReserve;
    }

    @Override
    public boolean updateReserve(ReservationDto dto) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        Student student = session.get(Student.class, dto.getStudentId());
        Room room = session.get(Room.class,dto.getRoomId());

        Reservation reservation = new Reservation(dto.getResId(),dto.getDate(),dto.getStatues(),student,room,dto.getKeyMoney(),dto.getAdvance());
        session.update(reservation);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean deleteReserve(String id) throws SQLException, ClassNotFoundException {
        return reserveDAO.delete(id);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return reserveDAO.generateNewId();
    }

    @Override
    public boolean existReserveID(String id) throws SQLException, ClassNotFoundException {
        return reserveDAO.exist(id);
    }

    @Override
    public boolean existStudent(String id) throws SQLException, ClassNotFoundException {
        return reserveDAO.existStudent(id);
    }

    @Override
    public boolean PurchaseRoom(ReservationDto dto) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Student student = session.get(Student.class,dto.getStudentId());
        Room room= session.get(Room.class, dto.getRoomId());

        Reservation reservation = new Reservation(dto.getResId(),dto.getDate(),dto.getStatues(),student,room,dto.getKeyMoney(),dto.getAdvance());
        session.save(reservation);

        room.setQty(room.getQty());
        session.update(room);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public StudentDto searchStudent(String id) throws SQLException, ClassNotFoundException {
        Student student=studentDAO.search(id);
        return new StudentDto(student.getStudentId(), student.getName(), student.getAddress(),student.getContactNo(),student.getDob(),student.getGender());

    }

    @Override
    public RoomDto searchRoom(String code) throws SQLException, ClassNotFoundException {
        Room room= roomDAO.search(code);
        return  new RoomDto(room.getRoomId(),room.getType(),room.getKeyMoney(),room.getQty());

    }

    @Override
    public boolean checkRoomIsAvailable(String code) throws SQLException, ClassNotFoundException {
        return roomDAO.exist(code);
    }

    @Override
    public boolean checkStudentIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return studentDAO.exist(id);
    }

    @Override
    public String generateNewReserveID() throws SQLException, ClassNotFoundException {
        return reserveDAO.generateNewId();
    }

    @Override
    public ArrayList<StudentDto> getAllStudents() throws SQLException, ClassNotFoundException {
        ArrayList<Student> all = studentDAO.getAll();
        ArrayList<StudentDto> allStudents = new ArrayList<>();
        for(Student student : all){
            allStudents.add(new StudentDto(student.getStudentId(),student.getName(),student.getAddress(),student.getContactNo(),student.getDob(),student.getGender()));

        }
        return allStudents;
    }

    @Override
    public ArrayList<RoomDto> getAllRooms() throws SQLException, ClassNotFoundException {
        ArrayList<Room> all= roomDAO.getAll();
        ArrayList<RoomDto> allRoom= new ArrayList<>();
        for (Room room : all){
            allRoom.add(new RoomDto(room.getRoomId(),room.getType(),room.getKeyMoney(),room.getQty()));

        }
        return allRoom;
    }
}
