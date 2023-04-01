package lk.ijse.hostelManagement.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class LoginController {
    public JFXTextField txtName;
    public JFXPasswordField txtPassword;
    public Label lblHide;

    public void loginOnAction(ActionEvent actionEvent) {
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
