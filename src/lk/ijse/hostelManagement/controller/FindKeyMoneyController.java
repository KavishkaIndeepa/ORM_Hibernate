package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostelManagement.bo.BOFactory;
import lk.ijse.hostelManagement.bo.custom.ReservationBO;
import lk.ijse.hostelManagement.dto.ReservationDto;
import lk.ijse.hostelManagement.util.NotificationController;
import lk.ijse.hostelManagement.util.UILoader;
import lk.ijse.hostelManagement.view.tm.ReservationTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FindKeyMoneyController implements Initializable {
    public AnchorPane keyMoneyPane;
    public TableView<ReservationTm> tblKeyMoney;
    public TableColumn colReserveId;
    public TableColumn colStudentId;
    public TableColumn colRoomId;
    public TableColumn colDate;
    public TableColumn colKeyMoney;
    public TableColumn colAdvance;
    public TableColumn colStatues;
    public JFXTextField txtSearch;

    private final ReservationBO reserveBO = (ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);


    public void searchOnAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (txtSearch.getText().trim().isEmpty()) {
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllReserve();
        } else {
            if (RegExit(txtSearch.getText())) {
                tblKeyMoney.getItems().clear();
                ArrayList<ReservationDto> arrayList = reserveBO.getAllReserveSearch(txtSearch.getText());
                if (arrayList != null) {
                    for (ReservationDto reservationDTO : arrayList) {
                        tblKeyMoney.getItems().add(new ReservationTm(reservationDTO.getResId(), reservationDTO.getDate()
                                , reservationDTO.getStatues(), reservationDTO.getStudentId(), reservationDTO.getRoomId(

                        ), reservationDTO.getKeyMoney(), reservationDTO.getAdvance()));
                    }
                }
            } else {
                tblKeyMoney.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        UILoader.NavigateToHome(keyMoneyPane, "AdminDashBoard");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblKeyMoney.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("resId"));
        tblKeyMoney.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("studentId"));
        tblKeyMoney.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("roomId"));
        tblKeyMoney.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblKeyMoney.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        tblKeyMoney.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("advance"));
        tblKeyMoney.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("statues"));
        loadAllReserve();
    }

    private void loadAllReserve() {
        tblKeyMoney.getItems().clear();
        /*Get all Reserve*/
        try {
            ArrayList<ReservationDto> allReserve = reserveBO.getAllReserve();
            for (ReservationDto reservationDTO : allReserve) {
                tblKeyMoney.getItems().add(new ReservationTm(reservationDTO.getResId(), reservationDTO.getDate(),
                        reservationDTO.getStatues(), reservationDTO.getStudentId(), reservationDTO.getRoomId(),
                        reservationDTO.getKeyMoney(), reservationDTO.getAdvance()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean RegExit(String id) throws SQLException, ClassNotFoundException {
        return reserveBO.existReserveID(id);
    }
}
