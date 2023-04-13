package lk.ijse.hostelManagement.bo.custom;

import lk.ijse.hostelManagement.bo.SuperBO;
import lk.ijse.hostelManagement.dto.StudentDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentBO extends SuperBO {
    ArrayList<StudentDto> getAllStudent() throws SQLException, ClassNotFoundException;

    ArrayList<StudentDto> searchAllStudent(String id) throws SQLException, ClassNotFoundException;

    boolean saveStudent(StudentDto dto) throws SQLException, ClassNotFoundException;

    boolean updateStudent(StudentDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteStudent(String id) throws SQLException, ClassNotFoundException;

    boolean existStudentID(String id) throws SQLException, ClassNotFoundException;
}
