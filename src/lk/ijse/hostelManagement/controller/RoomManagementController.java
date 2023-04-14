package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.hostelManagement.bo.custom.RoomBO;
import lk.ijse.hostelManagement.dto.RoomDto;
import lk.ijse.hostelManagement.util.NotificationController;
import lk.ijse.hostelManagement.util.UILoader;
import lk.ijse.hostelManagement.view.tm.RoomTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RoomManagementController implements Initializable {
    public AnchorPane RoomPane;
    public JFXTextField txtRoomId;
    public JFXTextField txtRoomType;
    public JFXTextField txtKeyMoney;
    public JFXTextField txtQty;
    public TableView<RoomTm> tblRoom;
    public TableColumn colRoomId;
    public TableColumn colRoomType;
    public TableColumn colKeyMoney;
    public TableColumn colQty;
    public TableColumn colKeyMoney1;
    public JFXTextField txtSearch;
    public JFXButton btnAddNew;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnBack;

    private final RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);


    public void addOnAction(ActionEvent actionEvent) {
        txtRoomId.setDisable(false);
        txtRoomType.setDisable(false);
        txtKeyMoney.setDisable(false);
        txtQty.setDisable(false);
        txtRoomId.clear();
        txtRoomType.clear();
        txtKeyMoney.clear();
        txtQty.clear();
        txtRoomId.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblRoom.getSelectionModel().clearSelection();
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String id = txtRoomId.getText();
        String type = txtRoomType.getText();
        int qty = Integer.parseInt(txtQty.getText());
        String key_money = txtKeyMoney.getText();

        if (!id.matches("^RM-[0-9]{3,4}$")) {
            NotificationController.Warring("Room ID", "Invalid Room ID.Check STU-000 type in your entered value.");
            txtRoomId.requestFocus();
            return;
        } else if (!type.matches("^([A-z]{1,9}|[A-z]{1,9}[ /|-]?[A-z]{1,9}[ /|-]?[A-z]{1,9})$")) {
            NotificationController.Warring("Room Type", "Invalid Room Type.");
            txtRoomType.requestFocus();
            return;
        } else if (!txtKeyMoney.getText().matches("^([A-Z a-z0-9]{4,8})$")) {
            NotificationController.Warring("Room Rent", "Invalid Room Rent.");
            txtKeyMoney.requestFocus();
            return;
        } else if (!txtQty.getText().matches("^[1-9][0-9]*$")) {
            NotificationController.Warring("Room Qty", "Invalid Room Qty");
            txtQty.requestFocus();
            return;
        }
        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Rooms*/
            try {
                if (exitRooms(id)) {
                    NotificationController.WarringError("Save Rooms Warning", id, "Already exists ");
                }
                roomBO.saveRooms(new RoomDto(id, type, key_money, qty));
                tblRoom.getItems().add(new RoomTm(id, type, key_money, qty));
                NotificationController.SuccessfulTableNotification("Save", "Rooms");
            } catch (SQLException e) {
                NotificationController.WarringError("Save Rooms Warning", id + e.getMessage(), "Failed to save the Rooms ");
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            /*Update Rooms*/
            try {
                if (!exitRooms(id)) {
                    NotificationController.WarringError("Update Rooms Warning", id, "There is no such Rooms associated with the ");
                }
                //Rooms update
                roomBO.updateRooms(new RoomDto(id, type, key_money, qty));
                NotificationController.SuccessfulTableNotification("Update", "Rooms");
            } catch (SQLException e) {
                NotificationController.WarringError("Update Rooms Warning", id + e.getMessage(), "Failed to update the Rooms ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            RoomTm selectedRoom = tblRoom.getSelectionModel().getSelectedItem();
            selectedRoom.setRoomTypeId(id);
            selectedRoom.setType(type);
            selectedRoom.setKeyMoney(key_money);
            selectedRoom.setQty(qty);
            tblRoom.refresh();
        }
        btnAddNew.fire();
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String code = tblRoom.getSelectionModel().getSelectedItem().getRoomTypeId();
        try {
            if (!exitRooms(code)) {
                NotificationController.WarringError("Delete Rooms Warning", code, "There is no such Rooms associated with the ");
            }
            roomBO.deleteRooms(code);
            tblRoom.getItems().remove(tblRoom.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "Rooms");
            tblRoom.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            NotificationController.WarringError("Delete Rooms Warning", code, "Failed to delete the Rooms ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        UILoader.NavigateToHome(RoomPane, "AdminDashBoard");
    }

    public void searchOnAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (txtSearch.getText().trim().isEmpty()) {
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllRoom();
        } else {
            if (exitRooms(txtSearch.getText())) {
                tblRoom.getItems().clear();
                ArrayList<RoomDto> arrayList = roomBO.searchAllRooms(txtSearch.getText());
                if (arrayList != null) {
                    for (RoomDto roomDTO : arrayList) {
                        tblRoom.getItems().add(new RoomTm(roomDTO.getRoomTypeId(), roomDTO.getType(), roomDTO.getKeyMoney(), roomDTO.getQty()));
                    }
                }
            } else {
                tblRoom.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }

    private boolean exitRooms(String id) throws SQLException, ClassNotFoundException {
        return roomBO.existRoomsID(id);
    }

    private void initUI() {
        txtRoomId.clear();
        txtRoomType.clear();
        txtKeyMoney.clear();
        txtQty.clear();

        txtRoomId.setDisable(true);
        txtRoomType.setDisable(true);
        txtKeyMoney.setDisable(true);
        txtQty.setDisable(true);

        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadAllRoom() {
        tblRoom.getItems().clear();
        /*Get all Room*/
        try {
            ArrayList<RoomDto> allRoom = roomBO.getAllRooms();
            for (RoomDto roomDTO : allRoom) {
                tblRoom.getItems().add(new RoomTm(roomDTO.getRoomTypeId(), roomDTO.getType(), roomDTO.getKeyMoney(), roomDTO.getQty()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblRoom.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
        tblRoom.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblRoom.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        tblRoom.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));

        initUI();

        tblRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                //------------------------Text Filed Load----------------------//
                txtRoomId.setText(newValue.getRoomTypeId());
                txtRoomType.setText(newValue.getType());
                txtKeyMoney.setText(newValue.getKeyMoney() + "");
                txtQty.setText(newValue.getQty() + "");

                txtRoomId.setDisable(false);
                txtRoomType.setDisable(false);
                txtKeyMoney.setDisable(false);
                txtQty.setDisable(false);
            }
        });

        txtQty.setOnAction(event -> btnSave.fire());
        loadAllRoom();
    }
}
