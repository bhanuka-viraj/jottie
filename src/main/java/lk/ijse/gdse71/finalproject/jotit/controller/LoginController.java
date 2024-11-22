package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse71.finalproject.jotit.AppInitializer;
import lk.ijse.gdse71.finalproject.jotit.dto.UserDto;
import lk.ijse.gdse71.finalproject.jotit.model.UserModel;
import lk.ijse.gdse71.finalproject.jotit.model.impl.UserModelImpl;
import lk.ijse.gdse71.finalproject.jotit.util.PasswordUtil;

import java.io.IOException;


public class LoginController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label lblPwdIncorrect;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    private final UserModel userModel = new UserModelImpl();
    protected static UserDto userDto ;

    public void initialize() {
        lblPwdIncorrect.setVisible(false);
    }

    @FXML
    void btnsignInOnAction(ActionEvent event) {
        if (txtUserName.getText().isEmpty()) {
            setBorderRed(txtUserName);
            new Alert(Alert.AlertType.WARNING, "Username is required.").showAndWait();
            return;
        }

        if (txtPassword.getText().isEmpty()) {
            resetBorderColors();
            setBorderRed(txtPassword);
            new Alert(Alert.AlertType.WARNING, "Password is required.").showAndWait();
            return;
        }

        verifyUser(txtUserName.getText(),txtPassword.getText());
    }

    private void verifyUser(String username, String pwd) {
        try {
            UserDto userDto = userModel.getUser(username);
            if (userDto == null) {
                lblPwdIncorrect.setVisible(true);
                setBorderRed(txtUserName);
                new Alert(Alert.AlertType.WARNING, "User not found",ButtonType.OK).showAndWait();
                return;
            }

            if (!PasswordUtil.checkPassword(pwd, userDto.getPassword())) {
                resetBorderColors();
                setBorderRed(txtPassword);
                new Alert(Alert.AlertType.WARNING, "Password is incorrect",ButtonType.OK).showAndWait();
                return;
            }

            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();

            LoginController.userDto = userDto;
            switchToMainLayout();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void switchToMainLayout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AppInitializer.class.getResource("/view/main_Layout.fxml"));
            Parent root = fxmlLoader.load();
            ControllerRef.layoutController = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("JoTtie");
            stage.setScene(scene);
            stage.show();
            stage.getIcons().add(new Image(AppInitializer.class.getResourceAsStream("/view/assets/logoPic.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            stage.setResizable(false);
            stage.setTitle("Sign up");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setBorderRed(Control control) {
        control.setStyle("-fx-border-color: ff3063;");
    }
    private void resetBorderColors() {
        txtUserName.setStyle("-fx-border-color: transparent transparent #2d4367 transparent;");
        txtPassword.setStyle("-fx-border-color: transparent transparent #2d4367 transparent;");
    }

}
