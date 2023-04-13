package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostelManagement.bo.BOFactory;
import lk.ijse.hostelManagement.bo.custom.ReservationBO;
import lk.ijse.hostelManagement.dto.ReservationDto;
import lk.ijse.hostelManagement.dto.RoomDto;
import lk.ijse.hostelManagement.dto.StudentDto;
import lk.ijse.hostelManagement.util.NotificationController;
import lk.ijse.hostelManagement.util.UILoader;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageReservationController implements Initializable {
    public AnchorPane ReservationPane;
    public JFXComboBox<String> cmbStudentId;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtAddress;
    public JFXTextField txtDob;
    public JFXTextField txtGender;
    public JFXComboBox<String> cmbRoomId;
    public JFXTextField txtRoomType;
    public JFXTextField txtKeyMoney;
    public JFXTextField txtQty;
    public JFXTextField txtAdvance;
    public Label llbResId;
    public JFXButton btnReserve;

    private String RegID;

    private final ReservationBO purchaseRoomBO = (ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);

    public void backOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        UILoader.NavigateToHome(ReservationPane, "AdminDashBoard");
    }


    public void addStudentOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        UILoader.NavigateToHome(ReservationPane, "StudentManagement");
    }

    public void reserveOnAction(ActionEvent actionEvent) {

        cmbStudentId.setDisable(false);
        cmbRoomId.setDisable(false);
        txtRoomType.setEditable(false);
        txtQty.setEditable(false);
        txtKeyMoney.setEditable(false);
        txtName.setEditable(false);
        txtQty.setEditable(false);
        txtAddress.setEditable(false);
        txtDob.setEditable(false);
        txtGender.setEditable(false);
        txtContact.setEditable(false);

        double roomFee = Double.parseDouble(txtKeyMoney.getText());
        double advance = Double.parseDouble(txtAdvance.getText());
        String status = String.valueOf(roomFee - advance);

        boolean b = saveReserve(RegID,LocalDate.now(),status,cmbStudentId.getValue(),cmbRoomId.getValue(),txtKeyMoney.getText(),advance);
        if (b) {
            NotificationController.SuccessfulTableNotification("Room Reserve", "Room Reserved in student ");
        } else {
            System.out.println(b);
            NotificationController.UnSuccessfulTableNotification("Room Reserve", "Room Reserved in student ");
        }


        RegID = generateNewOrderId(); //Generate id
        llbResId.setText(RegID);
        cmbRoomId.getSelectionModel().clearSelection();
        cmbStudentId.getSelectionModel().clearSelection();
        txtRoomType.clear();
        txtKeyMoney.clear();
        txtQty.clear();
        txtKeyMoney.clear();
        txtName.clear();
        txtAddress.clear();
        txtDob.clear();
        txtGender.clear();
        txtContact.clear();


    }

    public boolean saveReserve(String resId, LocalDate orderDate, String status, String stId, String roomId,String keyMoney, double advance) {
        try {
            return purchaseRoomBO.PurchaseRoom(new ReservationDto(resId, orderDate, status, stId, roomId, keyMoney, advance));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private String generateNewOrderId() {
        try {
            return purchaseRoomBO.generateNewReserveID();
        } catch (SQLException e) {
            NotificationController.Warring("Generate Order Id", "Failed to generate a new order id...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "REG-001";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RegID=generateNewOrderId();
        llbResId.setText(RegID);
        btnReserve.setDisable(true);
        loadAllRoomIds();
        loadAllStudentIds();

        //---------Student to Combo-------------//
        cmbStudentId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisableRegisterButton();

            if (newValue != null) {
                try {
                    if (!exitStudent(newValue + "")) {
                        NotificationController.WarringError("Search Student Warning", newValue, "There is no such student associated with the ");
                    }
                    /*Search student*/
                    StudentDto studentDTO = purchaseRoomBO.searchStudent(newValue + "");
                    txtName.setText(studentDTO.getName());
                    txtAddress.setText(studentDTO.getAddress());
                    txtDob.setText(studentDTO.getDob() + "");
                    txtGender.setText(studentDTO.getGender());
                    txtContact.setText(studentDTO.getContactNo());

                } catch (SQLException | ClassNotFoundException e) {
                    NotificationController.WarringError("Search Student Warning", newValue, "Failed to find the Student ");
                }
            } else {
                txtName.clear();
            }
        });


        //---------Room to Combo-------------//
        cmbRoomId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newRoomId) -> {
            btnReserve.setDisable(newRoomId == null);


            if (newRoomId != null) {
                try {
                    exitRooms(newRoomId + "");
                    RoomDto room = purchaseRoomBO.searchRoom(newRoomId + "");
                    txtRoomType.setText(room.getType());
                    txtQty.setText(String.valueOf(room.getQty()));
                    txtKeyMoney.setText(room.getKeyMoney());

                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }

            } else {
                txtKeyMoney.clear();
                txtQty.clear();
                txtName.clear();
                txtKeyMoney.clear();
            }
        });
    }




    private void loadAllRoomIds() {
        try {
            ArrayList<RoomDto> all = purchaseRoomBO.getAllRooms();
            for (RoomDto roomDTO : all) {
                cmbRoomId.getItems().add(roomDTO.getRoomTypeId());
            }
        } catch (SQLException e) {
            NotificationController.Warring("Rooms Load", "Failed to load customer ids.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllStudentIds() {
        try {
            ArrayList<StudentDto> all = purchaseRoomBO.getAllStudents();
            for (StudentDto studentDTO : all) {
                cmbStudentId.getItems().add(studentDTO.getStudentId());
            }

        } catch (SQLException e) {
            NotificationController.Warring("Student Load", "Failed to load customer ids.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void enableOrDisableRegisterButton() {
        btnReserve.setDisable(cmbRoomId.getSelectionModel().getSelectedItem() == null);

    }

    private boolean exitStudent(String id) throws SQLException, ClassNotFoundException {
        return purchaseRoomBO.checkStudentIsAvailable(id);
    }

    private boolean exitRooms(String id) throws SQLException, ClassNotFoundException {
        return purchaseRoomBO.checkRoomIsAvailable(id);
    }
}
