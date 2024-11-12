package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.gdse71.finalproject.jotit.dto.UserDto;
import lk.ijse.gdse71.finalproject.jotit.model.UserModel;
import lk.ijse.gdse71.finalproject.jotit.model.impl.UserModelImpl;
import lk.ijse.gdse71.finalproject.jotit.util.PasswordUtil;

import java.sql.Date;

public class RegisterController {
    @FXML
    private DatePicker dateDob;

    @FXML
    private Pane leftPane;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private PasswordField txtPassword1;

    @FXML
    private PasswordField txtPassword2;

    @FXML
    private TextField txtUserName;

    private final UserModel USER_MODEL= new UserModelImpl();

    @FXML
    public void initialize() {
        slideInComponents();
    }

    private void slideInComponents() {
        double slideDistance = 600;

        for (int i = 0; i < leftPane.getChildren().size(); i++) {
            var child = leftPane.getChildren().get(i);
            if (child != null) {
                child.setTranslateX(-slideDistance);

                TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), child);
                translateTransition.setDelay(Duration.millis(30 * i));
                translateTransition.setFromX(-slideDistance);
                translateTransition.setToX(0);
                translateTransition.play();
            }
        }
    }




    @FXML
    void btnsignInOnAction(ActionEvent event) {
        try {
            UserDto userDto = new UserDto();

            userDto.setFirstName(txtFirstName.getText());
            userDto.setLastName(txtLastName.getText());
            userDto.setEmail(txtEmail.getText());
            userDto.setUsername(txtUserName.getText());
            userDto.setDateOfBirth(Date.valueOf(dateDob.getValue()));


            if ((txtPassword1.getText().equals(txtPassword2.getText()))) {
                userDto.setPassword(PasswordUtil.hashPassword(txtPassword1.getText()));
                USER_MODEL.save(userDto);
            } else {
                txtPassword1.setStyle("-fx-text-fill: red; -fx-border-color: ff3063;-fx-border-radius: 25; -fx-background-radius: 25");
                txtPassword2.setStyle("-fx-text-fill: red; -fx-border-color: ff3063;-fx-border-radius: 25; -fx-background-radius: 25");
                new Alert(Alert.AlertType.WARNING, "Passwords do not match").showAndWait();
                txtPassword1.setStyle("-fx-text-fill: #000000; -fx-border-color: #c4c4c4;-fx-border-radius: 25; -fx-background-radius: 25");
                txtPassword2.setStyle("-fx-text-fill: #000000; -fx-border-color: #c4c4c4;-fx-border-radius: 25; -fx-background-radius: 25");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void linkSignInOnAction(ActionEvent event) {

    }


}
