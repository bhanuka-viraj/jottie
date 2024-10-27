package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    void btnsignInOnAction(ActionEvent event) {

    }
    @FXML
    void linkRegisterOnAction(ActionEvent event) {
        switchToRegisterPage();
    }


    private void switchToRegisterPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user/register.fxml"));
            Scene registerScene = new Scene(loader.load());

            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(registerScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
