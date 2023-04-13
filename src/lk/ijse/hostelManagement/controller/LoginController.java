package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hostelManagement.bo.BOFactory;
import lk.ijse.hostelManagement.bo.custom.UserBO;
import lk.ijse.hostelManagement.dto.LoginDto;
import lk.ijse.hostelManagement.util.NotificationController;
import lk.ijse.hostelManagement.util.UILoader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginController {
    public JFXTextField txtName;
    public JFXPasswordField txtPassword;
    public Label lblHide;
    public AnchorPane logging_pane;
    int attempts = 0;

    private final UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    public void loginOnAction(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        UILoader.LoginOnAction(logging_pane, "AdminDashBoard");
        NotificationController.LoginSuccessfulNotification("Admin");
        ArrayList<LoginDto> loginDTOS = userBO.getAllUsers();
        attempts++;
        loginDTOS.forEach(e -> {
            if (attempts <= 3) {
                if (e.getUserID().equals(txtName.getText()) && e.getPassword().equals(txtPassword.getText())) {
                    try {
                        UILoader.LoginOnAction(logging_pane, "DashBoard");
                        NotificationController.LoginSuccessfulNotification("Admin");
                    } catch (IOException | SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {

                }
            } else {
                txtName.setEditable(false);
                txtPassword.setEditable(false);
                NotificationController.LoginUnSuccessfulNotification("Account is Temporarily Disabled or You Did not Sign in Correctly.");
            }
        });
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void showPasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("/lk/ijse/hostelManagement/view/assest/show-password.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        txtPassword.setPromptText(txtPassword.getText());
        txtPassword.setText("");
        txtPassword.setDisable(true);
        txtPassword.requestFocus();
    }

    public void hidePasswordOnMousePressed(MouseEvent mouseEvent) {
        Image img = new Image("/lk/ijse/hostelManagement/view/assest/password hide.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        lblHide.setGraphic(view);

        txtPassword.setText(txtPassword.getPromptText());
        txtPassword.setPromptText("");
        txtPassword.setDisable(false);
    }
}
