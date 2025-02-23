package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.gdse71.finalproject.jotit.dto.JotDto;
import lk.ijse.gdse71.finalproject.jotit.dto.SharedJotDto;
import lk.ijse.gdse71.finalproject.jotit.dto.UserDto;
import lk.ijse.gdse71.finalproject.jotit.service.ServiceFactory;
import lk.ijse.gdse71.finalproject.jotit.service.ServiceType;
import lk.ijse.gdse71.finalproject.jotit.service.custom.SharedJotService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.UserService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.SharedJotServiceImpl;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.UserServiceImpl;
import lk.ijse.gdse71.finalproject.jotit.util.EmailUtil;
import lk.ijse.gdse71.finalproject.jotit.util.IdGenerator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class ShareController {

    @FXML
    private Label lblUser;

    @FXML
    private ComboBox<String> userCombo;

    private final UserService userService = (UserServiceImpl) ServiceFactory.getInstance().getService(ServiceType.USER);
    private final SharedJotService sharedJotService = (SharedJotServiceImpl)ServiceFactory.getInstance().getService(ServiceType.SHAREDJOT);
    private JotDto jotDto;

    public void initialize() {
        loadUserCombo();
    }

    @FXML
    void btnShareOnAction(ActionEvent event) {
        String selectedUsername = userCombo.getValue();

        if (selectedUsername != null) {
            try {

                String selectedUserId = userService.getUserIdByUsername(selectedUsername);

                if (jotDto != null) {
                    SharedJotDto sharedJotDto = new SharedJotDto();
                    sharedJotDto.setId(IdGenerator.generateId("SJ", 5));
                    sharedJotDto.setJotId(jotDto.getId());
                    sharedJotDto.setUserBy(LoginController.userDto.getId());
                    sharedJotDto.setUserWith(selectedUserId);
                    sharedJotDto.setDate(Date.valueOf(LocalDate.now()));

                    if (sharedJotService.save(sharedJotDto)) {
                        UserDto receiver = userService.getUserById(selectedUserId);
                        if (receiver != null) {
                            String receiverEmail = receiver.getEmail();
                            String subject = "You have a new jot!";
                            String body = "A jot has been shared with you by " + LoginController.userDto.getUsername() + ".";
                            EmailUtil.sendEmail(receiverEmail, subject, body);
                        }
                        Stage stage = (Stage) userCombo.getScene().getWindow();
                        stage.close();
                        new Alert(Alert.AlertType.INFORMATION, "Jot shared successfully!", ButtonType.OK).showAndWait();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to share jot.", ButtonType.OK).showAndWait();
                    }
                } else {
                    System.err.println("Error: JotDto is null. Cannot share.");
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error sharing jot: " + e.getMessage(), ButtonType.OK).showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a user to share with.", ButtonType.OK).showAndWait();
        }
    }

    public void loadUserCombo() {
        try {
            List<UserDto> users = userService.getAllUsers(LoginController.userDto.getId());
            for (UserDto user : users) {
                userCombo.getItems().add(user.getUsername());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error refreshing users: " + e.getMessage()).showAndWait();
        }
    }

    public void setJotDto(JotDto jotDto) {
        this.jotDto = jotDto;
    }
}
