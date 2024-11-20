package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.gdse71.finalproject.jotit.dto.UserDto;
import lk.ijse.gdse71.finalproject.jotit.model.UserModel;
import lk.ijse.gdse71.finalproject.jotit.model.impl.UserModelImpl;
import lk.ijse.gdse71.finalproject.jotit.util.IdGenerator;
import lk.ijse.gdse71.finalproject.jotit.util.PasswordUtil;

import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Pattern;

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

    @FXML
    private Label lblPwdIncorrect;

    private final UserModel userModel = new UserModelImpl();

    @FXML
    public void initialize() {
        slideInComponents();
        lblPwdIncorrect.setVisible(false);
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

            if (txtFirstName.getText().isEmpty() || !txtFirstName.getText().matches("[a-zA-Z]+")) {
                setBorderRed(txtFirstName);
                showAlert(Alert.AlertType.WARNING, "Invalid first name. Only letters allowed.");
                return;
            }

            if (txtLastName.getText().isEmpty() || !txtLastName.getText().matches("[a-zA-Z]+")) {
                resetBorderColors();
                setBorderRed(txtLastName);
                showAlert(Alert.AlertType.WARNING, "Invalid last name. Only letters allowed.");
                return;
            }
            if (dateDob.getValue() == null || dateDob.getValue().isAfter(LocalDate.now())) {
                resetBorderColors();
                setBorderRed(dateDob);
                showAlert(Alert.AlertType.WARNING, "Invalid date of birth. Cannot be in the future.");
                return;
            }

            if (txtUserName.getText().isEmpty() || !txtUserName.getText().matches("[a-zA-Z0-9]+")) {
                resetBorderColors();
                setBorderRed(txtUserName);
                showAlert(Alert.AlertType.WARNING, "Invalid username. No spaces or special characters allowed.");
                return;
            }

            if (txtEmail.getText().isEmpty() || !isValidEmail(txtEmail.getText())) {
                resetBorderColors();
                setBorderRed(txtEmail);
                showAlert(Alert.AlertType.WARNING, "Invalid email format.");
                return;
            }

            if (txtPassword1.getText().isEmpty() || txtPassword2.getText().isEmpty()) {
                resetBorderColors();
                setBorderRed(txtPassword1);
                setBorderRed(txtPassword2);
                lblPwdIncorrect.setVisible(true);
                showAlert(Alert.AlertType.WARNING, "Passwords field cannot be empty");
                return;
            }

            if (!txtPassword1.getText().equals(txtPassword2.getText())) {
                resetBorderColors();
                setBorderRed(txtPassword1);
                setBorderRed(txtPassword2);
                lblPwdIncorrect.setVisible(true);
                showAlert(Alert.AlertType.WARNING, "Passwords do not match");
                return;

            }
            UserDto userDto = new UserDto();

            userDto.setId(IdGenerator.generateId("USR", 5));
            userDto.setFirstName(txtFirstName.getText());
            userDto.setLastName(txtLastName.getText());
            userDto.setEmail(txtEmail.getText());
            userDto.setUsername(txtUserName.getText());
            userDto.setDateOfBirth(Date.valueOf(dateDob.getValue()));

            userDto.setPassword(PasswordUtil.hashPassword(txtPassword1.getText()));
            if (userModel.save(userDto)) {
                showAlert(Alert.AlertType.INFORMATION, "Successfully registered.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void linkSignInOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user/login.fxml"));
            Scene login = new Scene(loader.load());

            Stage stage = (Stage) leftPane.getScene().getWindow();
            stage.setScene(login);
            stage.setTitle("Sign in");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBorderRed(Control control) {
        control.setStyle("-fx-border-color: ff3063;");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);

        return pattern.matcher(email).matches();
    }

    private void resetBorderColors() {
        lblPwdIncorrect.setVisible(false);
        txtFirstName.setStyle("-fx-border-color: transparent transparent #2d4367 transparent;");
        txtPassword1.setStyle("-fx-border-color: transparent transparent #2d4367 transparent;");
        txtEmail.setStyle("-fx-border-color: transparent transparent #2d4367 transparent;");
        txtLastName.setStyle("-fx-border-color: transparent transparent #2d4367 transparent;");
        txtUserName.setStyle("-fx-border-color: transparent transparent #2d4367 transparent;");
        dateDob.setStyle("-fx-border-color: transparent transparent #2d4367 transparent;");
    }

    public void showAlert(Alert.AlertType alertType, String message) {
        new Alert(alertType, message, ButtonType.OK).showAndWait();
    }

}
