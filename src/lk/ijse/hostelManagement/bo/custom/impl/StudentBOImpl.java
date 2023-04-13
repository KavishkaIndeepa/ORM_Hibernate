package lk.ijse.hostelManagement.bo.custom.impl;

import lk.ijse.hostelManagement.bo.custom.StudentBO;
import lk.ijse.hostelManagement.dao.DAOFactory;
import lk.ijse.hostelManagement.dao.custom.StudentDAO;
import lk.ijse.hostelManagement.dto.StudentDto;
import lk.ijse.hostelManagement.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentBOImpl implements StudentBO {

    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);


    @Override
    public ArrayList<StudentDto> getAllStudent() throws SQLException, ClassNotFoundException {
        ArrayList<Student> all = studentDAO.getAll();
        ArrayList<StudentDto> allStudent = new ArrayList<>();
        for (Student student : all) {
            allStudent.add(new StudentDto(student.getStudentId(), student.getName(), student.getAddress(), student.getContactNo(), student.getDob(), student.getGender()));
        }
        return allStudent;
    }

    @Override
    public ArrayList<StudentDto> searchAllStudent(String id) throws SQLException, ClassNotFoundException {
        Student all = studentDAO.search(id);
        ArrayList<StudentDto> allSStudent = new ArrayList<>();
        allSStudent.add(new StudentDto(all.getStudentId(), all.getName(), all.getAddress(), all.getContactNo(), all.getDob(), all.getGender()));
        return allSStudent;
    }

    @Override
    public boolean saveStudent(StudentDto dto) throws SQLException, ClassNotFoundException {
        return studentDAO.save(new Student(dto.getStudentId(), dto.getName(), dto.getAddress(), dto.getContactNo(), dto.getDob(), dto.getGender()));
    }

    @Override
    public boolean updateStudent(StudentDto dto) throws SQLException, ClassNotFoundException {
        return studentDAO.update(new Student(dto.getStudentId(), dto.getName(), dto.getAddress(), dto.getContactNo(), dto.getDob(), dto.getGender()));
    }

    @Override
    public boolean deleteStudent(String id) throws SQLException, ClassNotFoundException {
        return studentDAO.delete(id);
    }

    @Override
    public boolean existStudentID(String id) throws SQLException, ClassNotFoundException {
        return studentDAO.exist(id);
    }
}
