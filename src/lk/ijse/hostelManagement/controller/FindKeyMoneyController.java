package lk.ijse.hostelManagement.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostelManagement.util.UILoader;

import java.io.IOException;
import java.sql.SQLException;

public class FindKeyMoneyController {
    public AnchorPane keyMoneyPane;
    public TableView tblKeyMoney;
    public TableColumn colReserveId;
    public TableColumn colStudentId;
    public TableColumn colRoomId;
    public TableColumn colDate;
    public TableColumn colKeyMoney;
    public TableColumn colAdvance;
    public TableColumn colStatues;

    public void searchOnAction(KeyEvent keyEvent) {
    }

    public void backOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        UILoader.NavigateToHome(keyMoneyPane, "AdminDashBoard");
    }
}
