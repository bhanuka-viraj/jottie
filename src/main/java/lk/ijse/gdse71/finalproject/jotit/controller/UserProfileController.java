package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gdse71.finalproject.jotit.dto.RelationshipDto;
import lk.ijse.gdse71.finalproject.jotit.dto.UserDto;
import lk.ijse.gdse71.finalproject.jotit.dto.UserRelationshipDto;
import lk.ijse.gdse71.finalproject.jotit.service.custom.RelationshipService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.UserService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.RelationshipServiceImpl;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.UserServiceImpl;
import lk.ijse.gdse71.finalproject.jotit.util.IdGenerator;
import lk.ijse.gdse71.finalproject.jotit.util.PasswordUtil;

import java.sql.Date;
import java.util.List;

public class UserProfileController {

    @FXML
    private DatePicker dateDob;

    @FXML
    private PasswordField txCrntPwd;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private PasswordField txtNewPwd;
    @FXML
    private ComboBox<String> userCombo;
    @FXML
    private ComboBox<RelationshipDto> relationshipCombo;

    @FXML
    private TextField txtUserName;
    private UserDto userDto = LoginController.userDto;
    private final UserService userService = new UserServiceImpl();
    private final RelationshipService relationshipService = new RelationshipServiceImpl();

    public void initialize() {
        setDetails();
        refreshRelationshipCombo();
        refreshUserCombo();
        Platform.runLater(() -> txtFirstName.requestFocus());
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            boolean isFieldUpdated = false;

            if (!txtFirstName.getText().equals(userDto.getFirstName())) {
                userDto.setFirstName(txtFirstName.getText());
                isFieldUpdated = true;
            }

            if (!txtLastName.getText().equals(userDto.getLastName())) {
                userDto.setLastName(txtLastName.getText());
                isFieldUpdated = true;
            }

            if (!txtEmail.getText().equals(userDto.getEmail())) {
                userDto.setEmail(txtEmail.getText());
                isFieldUpdated = true;
            }

            if (!txtUserName.getText().equals(userDto.getUsername())) {
                userDto.setUsername(txtUserName.getText());
                isFieldUpdated = true;
            }

            if (!dateDob.getValue().isEqual(userDto.getDateOfBirth().toLocalDate())) {
                userDto.setDateOfBirth(Date.valueOf(dateDob.getValue()));
                isFieldUpdated = true;
            }

            String selectedUser = userCombo.getValue();
            RelationshipDto selectedRelationship = relationshipCombo.getValue();

            if (selectedUser != null && selectedRelationship != null) {

                String userId = userService.getUserIdByUsername(selectedUser);

                UserRelationshipDto userRelationshipDto = new UserRelationshipDto();
                userRelationshipDto.setId(IdGenerator.generateId("USREL",5));
                userRelationshipDto.setUserId(userId);
                userRelationshipDto.setRelationshipId(selectedRelationship.getId());
                userRelationshipDto.setAddedById(LoginController.userDto.getId());

                if (userService.saveUserRelationship(userRelationshipDto)) {
                    new Alert(Alert.AlertType.INFORMATION, "Relationship added successfully ." ,ButtonType.OK).show();
                    userCombo.setValue("");
                    relationshipCombo.setValue(null);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Relationship cannot be added." ,ButtonType.OK).show();
                }
            }
            if (!txtNewPwd.getText().isEmpty()) {
                if (!txCrntPwd.getText().isEmpty()) {
                    if (PasswordUtil.checkPassword(txCrntPwd.getText(), userDto.getPassword())) {
                        userDto.setPassword(PasswordUtil.hashPassword(txtNewPwd.getText()));
                        isFieldUpdated = true;
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Current password is incorrect.", ButtonType.OK).showAndWait();
                        return;
                    }
                } else {
                    new Alert(Alert.AlertType.WARNING, "Please enter your current password to change it.", ButtonType.OK).showAndWait();
                    return;
                }
            }

            if (isFieldUpdated) {
                if (userService.update(userDto)){
                    clearFields();
                    new Alert(Alert.AlertType.INFORMATION, "Profile updated successfully!", ButtonType.OK).showAndWait();
                }else {
                    new Alert(Alert.AlertType.WARNING, "Profile cannot be updated.", ButtonType.OK).showAndWait();
                }

            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error updating profile: " + e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }
    @FXML
    void addRelationshipOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addRelationship.fxml"));
            Parent parent = loader.load();

            AddRelationshipController addRelationshipController=loader.getController();
            addRelationshipController.setUserProfileController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Add Relationship");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshRelationshipCombo() {
        try {
            relationshipCombo.getItems().clear();
            List<RelationshipDto> relationships = relationshipService.getAllRelationships(LoginController.userDto.getId());
            ObservableList<RelationshipDto> observableList = FXCollections.observableArrayList(relationships);
            relationshipCombo.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error refreshing relationships: " + e.getMessage()).showAndWait();
        }
    }

    public void refreshUserCombo() {
        try {
            userCombo.getItems().clear();
            List<UserDto> users = userService.getAllUsers(LoginController.userDto.getId());
            for (UserDto user : users) {
                userCombo.getItems().add(user.getUsername());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error refreshing users: " + e.getMessage()).showAndWait();
        }
    }


    public void setDetails(){
        txtFirstName.setText(userDto.getFirstName());
        txtLastName.setText(userDto.getLastName());
        txtEmail.setText(userDto.getEmail());
        txtUserName.setText(userDto.getUsername());
        dateDob.setValue(userDto.getDateOfBirth().toLocalDate());

    }

    public void clearFields(){
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        txtUserName.clear();
        txCrntPwd.clear();
        txtNewPwd.clear();
        dateDob.setValue(null);
    }
}
