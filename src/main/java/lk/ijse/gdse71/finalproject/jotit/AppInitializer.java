package lk.ijse.gdse71.finalproject.jotit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lk.ijse.gdse71.finalproject.jotit.controller.ControllerRef;
import lk.ijse.gdse71.finalproject.jotit.util.EmailUtil;

import java.io.IOException;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/user/login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("JoTtie");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/view/assets/logoPic.png")));
    }

    public static void main(String[] args) {
        launch();
    }
}