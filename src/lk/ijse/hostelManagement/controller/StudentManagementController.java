package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostelManagement.util.UILoader;

import java.io.IOException;
import java.sql.SQLException;

public class StudentManagementController {
    public AnchorPane StudentPane;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNo;
    public JFXDatePicker dateOfBirth;
    public JFXComboBox comGender;
    public TableView tblStudent;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colAddress;
    public TableColumn colContactNo;
    public TableColumn colDob;
    public TableColumn colGender;
    public JFXTextField txtSearch;

   
    public void AddOnAction(ActionEvent actionEvent) {
    }

    public void SaveOnAction(ActionEvent actionEvent) {
    }

    public void DeleteOnAction(ActionEvent actionEvent) {
    }

    public void BackOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        UILoader.NavigateToHome(StudentPane, "ManageReservation");
    }


    public void SearchOnAction(KeyEvent keyEvent) {
    }
}
