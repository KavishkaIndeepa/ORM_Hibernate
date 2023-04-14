package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostelManagement.bo.BOFactory;
import lk.ijse.hostelManagement.bo.custom.UserBO;
import lk.ijse.hostelManagement.dto.LoginDto;
import lk.ijse.hostelManagement.util.NotificationController;
import lk.ijse.hostelManagement.util.UILoader;
import lk.ijse.hostelManagement.view.tm.LoginTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageUserController implements Initializable {
    public AnchorPane UserPane;
    public JFXTextField txtUserId;
    public JFXTextField txtUserName;
    public JFXTextField txtUserAddress;
    public JFXTextField txtContactNo;
    public JFXTextField txtPassword;
    public JFXComboBox<String> cmbGender;
    public TableView<LoginTm> tblUser;
    public TableColumn colUserId;
    public TableColumn colUserName;
    public TableColumn colAddress;
    public TableColumn colContactNo;
    public TableColumn colPassword;
    public TableColumn colGender;


    private final UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    public JFXButton btnAdd;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnBack;

    public void addOnAction(ActionEvent actionEvent) {
        txtUserId.setDisable(false);
        txtUserName.setDisable(false);
        txtContactNo.setDisable(false);
        txtUserAddress.setDisable(false);
        txtPassword.setDisable(false);
        cmbGender.setDisable(false);

        txtUserId.clear();
        txtUserName.clear();
        txtContactNo.clear();
        txtUserAddress.clear();
        txtPassword.clear();
        cmbGender.getSelectionModel().clearSelection();
        txtUserId.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblUser.getSelectionModel().clearSelection();
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String id = txtUserId.getText();
        String name = txtUserName.getText();
        String cNO = txtContactNo.getText();
        String address = txtUserAddress.getText();
        String pws = txtPassword.getText();
        String gender = cmbGender.getValue();


        if (!id.matches("^([A-z 0-9]{6,20})$")) {
            NotificationController.Warring("User Id", "Invalid User Id.");
            txtUserId.requestFocus();
            return;
        } else if (!name.matches("^([A-Z a-z]{5,40})$")) {
            NotificationController.Warring("Student Name", "User Name.");
            txtUserName.requestFocus();
            return;
        } else if (!cNO.matches("^(07(0|1|2|4|5|6|7|8)[0-9]{7})$")) {
            NotificationController.Warring("Contact Number", "Invalid User Contact Number.");
            txtContactNo.requestFocus();
            return;
        } else if (!address.matches("^([A-Za-z]{4,10})$")) {
            NotificationController.Warring("Address", "Invalid User Address.");
            txtUserAddress.requestFocus();
            return;
        } else if (!pws.matches("^([A-z 0-9]{4,20})$")) {
            NotificationController.Warring("Password", "Invalid User Password.");
            txtPassword.requestFocus();
            return;
        } else if (!gender.matches("^([A-Z a-z]{4,20})$")) {
            NotificationController.Warring("Gender", "Invalid User Gender.");
            cmbGender.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save User*/
            try {
                if (exitUser(id)) {
                    NotificationController.WarringError("Save User Warning", id, "Already exists ");
                }
                userBO.saveUser(new LoginDto(id, name, address, cNO, pws, gender));
                tblUser.getItems().add(new LoginTm(id, name, address, cNO, pws, gender));
                NotificationController.SuccessfulTableNotification("Save", "User");
            } catch (SQLException e) {
                NotificationController.WarringError("Save User Warning", id + e.getMessage(), "Failed to save the User ");
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            /*Update User*/
            try {
                if (!exitUser(id)) {
                    NotificationController.WarringError("Update User Warning", id, "There is no such User associated with the ");
                }
                //User update
                userBO.updateUser(new LoginDto(id, name, address, cNO, pws, gender));
                NotificationController.SuccessfulTableNotification("Update", "User");
            } catch (SQLException e) {
                NotificationController.WarringError("Update User Warning", id + e.getMessage(), "Failed to update the User ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            LoginTm selectedItem = tblUser.getSelectionModel().getSelectedItem();
            selectedItem.setUserID(id);
            selectedItem.setName(name);
            selectedItem.setAddress(address);
            selectedItem.setContact_no(cNO);
            selectedItem.setPassword(pws);
            selectedItem.setGender(gender);
            tblUser.refresh();
        }
        btnAdd.fire();
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String code = tblUser.getSelectionModel().getSelectedItem().getUserID();
        try {
            if (!exitUser(code)) {
                NotificationController.WarringError("Delete User Warning", code, "There is no such User associated with the ");
            }
            userBO.deleteUser(code);
            tblUser.getItems().remove(tblUser.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "User");
            tblUser.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            NotificationController.WarringError("Delete User Warning", code, "Failed to delete the User ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        UILoader.NavigateToHome(UserPane, "AdminDashBoard");
    }

    private boolean exitUser(String id) throws SQLException, ClassNotFoundException {
        return userBO.existUser(id);
    }

    private void initUI() {
        txtUserId.clear();
        txtUserName.clear();
        txtContactNo.clear();
        txtUserAddress.clear();
        txtPassword.clear();
        cmbGender.getSelectionModel().clearSelection();

        txtUserId.setDisable(true);
        txtUserName.setDisable(true);
        txtContactNo.setDisable(true);
        txtUserAddress.setDisable(true);
        txtPassword.setDisable(true);
        cmbGender.setDisable(true);

        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbGender.getItems().addAll("Male", "Female", "Other");

        tblUser.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("userID"));
        tblUser.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblUser.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblUser.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact_no"));
        tblUser.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("Password"));
        tblUser.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gender"));

        initUI();

        tblUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                //------------------------Text Filed Load----------------------//
                txtUserId.setText(newValue.getUserID());
                txtUserName.setText(newValue.getName());
                txtContactNo.setText(newValue.getContact_no());
                txtUserAddress.setText(newValue.getAddress());
                txtPassword.setText(newValue.getPassword());
                cmbGender.setValue(newValue.getGender() + "");

                txtUserId.setDisable(false);
                txtUserName.setDisable(false);
                txtContactNo.setDisable(false);
                txtUserAddress.setDisable(false);
                txtPassword.setDisable(false);
                cmbGender.setDisable(false);
            }
        });

        txtUserAddress.setOnAction(event -> btnSave.fire());
        laodAllUsers();
    }

    private void laodAllUsers() {
        tblUser.getItems().clear();
        /*Get all Student*/
        try {
            ArrayList<LoginDto> allUsers = userBO.getAllUsers();
            for (LoginDto loginDTO : allUsers) {
                tblUser.getItems().add(new LoginTm(loginDTO.getUserID(), loginDTO.getName(), loginDTO.getAddress(), loginDTO.getContact_no(), loginDTO.getPassword(), loginDTO.getGender()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
