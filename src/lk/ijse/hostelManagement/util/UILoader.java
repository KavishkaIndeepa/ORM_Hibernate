package lk.ijse.hostelManagement.util;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class UILoader {

    public static void loadUiDashBoard(AnchorPane anchorPane, String location) throws IOException {
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(UILoader.class.getResource("/lk/ijse/hostelManagement/view/" + location + ".fxml"))));
    }

    public static void LoginOnAction(AnchorPane anchorPane, String location) throws IOException, SQLException {

        Stage window = (Stage) anchorPane.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(UILoader.class.getResource("/lk/ijse/hostelManagement/view/" + location + ".fxml")))));
    }

    public static void NavigateToHome(AnchorPane anchorPane, String location) throws IOException, SQLException {

        Stage primaryStage = (Stage) (anchorPane.getScene().getWindow());
        primaryStage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(UILoader.class.getResource("/lk/ijse/hostelManagement/view/" + location + ".fxml")))));
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }
}
