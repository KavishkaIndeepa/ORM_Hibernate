package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostelManagement.bo.BOFactory;
import lk.ijse.hostelManagement.bo.custom.StudentBO;
import lk.ijse.hostelManagement.dto.StudentDto;
import lk.ijse.hostelManagement.util.NotificationController;
import lk.ijse.hostelManagement.util.UILoader;
import lk.ijse.hostelManagement.view.tm.StudentTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class StudentManagementController implements Initializable {

    private final StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);

    public AnchorPane StudentPane;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNo;
    public JFXDatePicker dateOfBirth;
    public JFXComboBox comGender;
    public TableView<StudentTm> tblStudent;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colAddress;
    public TableColumn colContactNo;
    public TableColumn colDob;
    public TableColumn colGender;
    public JFXTextField txtSearch;
    public JFXButton btnDelete;
    public JFXButton btnSave;
    public JFXButton btnAdd;


    public void AddOnAction(ActionEvent actionEvent) {
        txtId.setDisable(false);
        txtName.setDisable(false);
        txtContactNo.setDisable(false);
        txtAddress.setDisable(false);
        dateOfBirth.setDisable(false);
        comGender.setDisable(false);

        txtId.clear();
        txtName.clear();
        txtContactNo.clear();
        txtAddress.clear();
        dateOfBirth.getEditor().clear();
        comGender.getSelectionModel().clearSelection();
        txtId.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblStudent.getSelectionModel().clearSelection();
    }

    public void SaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String cNO = txtContactNo.getText();
        String address = txtAddress.getText();
        LocalDate dob = dateOfBirth.getValue();
        String gender = (String) comGender.getValue();


        if (!id.matches("^(ST-[0-9]{3,4})$")) {
            NotificationController.Warring("Student Id", "Invalid Student Id.Check STU-000 type in your entered value.");
            txtId.requestFocus();
            return;
        } else if (!name.matches("^([A-Z a-z]{5,40})$")) {
            NotificationController.Warring("Student Name", "Student Name.");
            txtName.requestFocus();
            return;
        } else if (!cNO.matches("^(07(0|1|2|4|5|6|7|8)[0-9]{7})$")) {
            NotificationController.Warring("Contact Number", "Invalid Student Contact Number.");
            txtContactNo.requestFocus();
            return;
        } else if (!address.matches("^([A-Za-z]{4,10})$")) {
            NotificationController.Warring("Address", "Invalid Student Address.");
            txtAddress.requestFocus();
            return;
        } else if (!gender.matches("^([A-Z a-z]{4,20})$")) {
            NotificationController.Warring("Gender", "Invalid Student Gender.");
            comGender.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Student*/
            try {
                if (exitStudent(id)) {
                    NotificationController.WarringError("Save Student Warning", id, "Already exists ");
                }
                studentBO.saveStudent(new StudentDto(id, name, address, cNO, dob, gender));
                tblStudent.getItems().add(new StudentTm(id, name, address, cNO, dob, gender));
                NotificationController.SuccessfulTableNotification("Save", "Student");
            } catch (SQLException e) {
                NotificationController.WarringError("Save Student Warning", id + e.getMessage(), "Failed to save the Student ");
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            /*Update Students*/
            try {
                if (!exitStudent(id)) {
                    NotificationController.WarringError("Update Student Warning", id, "There is no such Student associated with the ");
                }
                //Students update
                studentBO.updateStudent(new StudentDto(id, name, address, cNO, dob, gender));
                NotificationController.SuccessfulTableNotification("Update", "Student");
            } catch (SQLException e) {
                NotificationController.WarringError("Update Student Warning", id + e.getMessage(), "Failed to update the Student ");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            StudentTm selectedItem = tblStudent.getSelectionModel().getSelectedItem();
            selectedItem.setStudentId(id);
            selectedItem.setName(name);
            selectedItem.setAddress(address);
            selectedItem.setContactNo(cNO);
            selectedItem.setDob(dob);
            selectedItem.setGender(gender);
            tblStudent.refresh();
        }
        btnAdd.fire();
    }

    public void DeleteOnAction(ActionEvent actionEvent) {
        String code = tblStudent.getSelectionModel().getSelectedItem().getStudentId();;
        try {
            if (!exitStudent(code)) {
                NotificationController.WarringError("Delete Student Warning", code, "There is no such Student associated with the ");
            }
            studentBO.deleteStudent(code);
            tblStudent.getItems().remove(tblStudent.getSelectionModel().getSelectedItem());
            NotificationController.SuccessfulTableNotification("Delete", "Student");
            tblStudent.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            NotificationController.WarringError("Delete Student Warning", code, "Failed to delete the Student ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void BackOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        UILoader.NavigateToHome(StudentPane, "ManageReservation");
    }


    public void SearchOnAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        if (txtSearch.getText().trim().isEmpty()) {
            NotificationController.Warring("Empty Search Id", "Please Enter Current ID.");
            loadAllStudent();
        } else {
            if (exitStudent(txtSearch.getText())) {
                tblStudent.getItems().clear();
                ArrayList<StudentDto> arrayList = studentBO.searchAllStudent(txtSearch.getText());
                if (arrayList != null) {
                    for (StudentDto studentDTO : arrayList) {
                        tblStudent.getItems().add(new StudentTm(studentDTO.getStudentId(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContactNo(), studentDTO.getDob(), studentDTO.getGender()));
                    }
                }
            } else {
                tblStudent.getItems().clear();
                NotificationController.Warring("Empty Data Set", "Please Enter Current ID.");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comGender.getItems().addAll("Male", "Female", "Other");

        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("studentId"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("dob"));
        tblStudent.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gender"));

        initUI();

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                //------------------------Text Filed Load----------------------//
                txtId.setText(newValue.getStudentId());
                txtName.setText(newValue.getName());
                txtContactNo.setText(newValue.getContactNo());
                txtAddress.setText(newValue.getAddress());
                dateOfBirth.setValue(newValue.getDob());
                comGender.setValue(newValue.getGender() + "");

                txtId.setDisable(false);
                txtName.setDisable(false);
                txtContactNo.setDisable(false);
                txtAddress.setDisable(false);
                dateOfBirth.setDisable(false);
                comGender.setDisable(false);
            }
        });

        txtAddress.setOnAction(event -> btnSave.fire());
        loadAllStudent();
    }


    private boolean exitStudent(String id) throws SQLException, ClassNotFoundException {
        return studentBO.existStudentID(id);
    }

    private void initUI() {
        txtId.clear();
        txtName.clear();
        txtContactNo.clear();
        txtAddress.clear();
        dateOfBirth.getEditor().clear();
        comGender.getSelectionModel().clearSelection();

        txtId.setDisable(true);
        txtName.setDisable(true);
        txtContactNo.setDisable(true);
        txtAddress.setDisable(true);
        dateOfBirth.setDisable(true);
        comGender.setDisable(true);

        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadAllStudent() {
        tblStudent.getItems().clear();
        /*Get all Student*/
        try {
            ArrayList<StudentDto> allStudent = studentBO.getAllStudent();
            for (StudentDto studentDTO : allStudent) {
                tblStudent.getItems().add(new StudentTm(studentDTO.getStudentId(), studentDTO.getName(), studentDTO.getAddress(), studentDTO.getContactNo(), studentDTO.getDob(), studentDTO.getGender()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


}
