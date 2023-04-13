package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostelManagement.util.UILoader;

import java.io.IOException;
import java.sql.SQLException;

public class ManageUserController {
    public AnchorPane UserPane;
    public JFXTextField txtUserId;
    public JFXTextField txtUserName;
    public JFXTextField txtUserAddress;
    public JFXTextField txtContactNo;
    public JFXTextField txtPassword;
    public JFXComboBox cmbGender;
    public TableView tblUser;
    public TableColumn colUserId;
    public TableColumn colUserName;
    public TableColumn colAddress;
    public TableColumn colContactNo;
    public TableColumn colPassword;
    public TableColumn colGender;

    public void addOnAction(ActionEvent actionEvent) {
    }

    public void saveOnAction(ActionEvent actionEvent) {
    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void backOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        UILoader.NavigateToHome(UserPane, "AdminDashBoard");
    }
}
