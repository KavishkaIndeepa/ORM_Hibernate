package lk.ijse.hostelManagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/lk/ijse/hostelManagement/view/login.fxml")))));
        primaryStage.centerOnScreen();
       // primaryStage.initStyle(StageStyle.UNDECORATED); (close button left)
        primaryStage.show();
    }
}
