package lk.ijse.gdse71.finalproject.jotit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/mainLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("JoTtie");
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image(AppInitializer.class.getResourceAsStream("/view/assets/logoPic.png")));
    }

    public static void main(String[] args) {
        launch();
    }
}