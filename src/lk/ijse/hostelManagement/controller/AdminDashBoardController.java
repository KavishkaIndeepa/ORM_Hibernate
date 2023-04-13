package lk.ijse.hostelManagement.controller;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.hostelManagement.util.UILoader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class AdminDashBoardController {
    public AnchorPane adminDashBoard;
    public ImageView imgReservation;
    public ImageView imgRoom;
    public ImageView imgKeyMoney;
    public Label lblMenu;
    public Label lblDescription;

    public void manageUserOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        UILoader.NavigateToHome(adminDashBoard, "ManageUser");
    }

    public void backOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        UILoader.NavigateToHome(adminDashBoard, "login");
    }

    public void navigate(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            Parent root = null;

            switch (icon.getId()) {
                case "imgReservation":
                    root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/lk/ijse/hostelManagement/view/ManageReservation.fxml")));
                    break;
                case "imgRoom":
                    root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/lk/ijse/hostelManagement/view/RoomManagement.fxml")));
                    break;
                case "imgKeyMoney":
                    root = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/lk/ijse/hostelManagement/view/FindKeyMoney.fxml")));
                    break;
            }

            if (root != null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.adminDashBoard.getScene().getWindow();
                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();
            }
        }
    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "imgReservation":
                    lblMenu.setText("Manage Registration");
                    lblDescription.setText("Click to Make Registration and Add, Update, Delete Student");
                    break;
                case "imgRoom":
                    lblMenu.setText("Manage Rooms");
                    lblDescription.setText("Click to Add, Update, Delete Rooms");
                    break;
                case "imgKeyMoney":
                    lblMenu.setText("Find  Key Money");
                    lblDescription.setText("Click to find remain key money student");
                    break;
            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.BLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    public void playMouseExitAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }
}
