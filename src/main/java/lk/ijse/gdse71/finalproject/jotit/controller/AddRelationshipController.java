package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.gdse71.finalproject.jotit.dto.RelationshipDto;
import lk.ijse.gdse71.finalproject.jotit.service.ServiceFactory;
import lk.ijse.gdse71.finalproject.jotit.service.ServiceType;
import lk.ijse.gdse71.finalproject.jotit.service.custom.RelationshipService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.RelationshipServiceImpl;
import lk.ijse.gdse71.finalproject.jotit.util.IdGenerator;

public class AddRelationshipController {

    @FXML
    private TextField txtRelationship;
    private final RelationshipService relationshipService = (RelationshipServiceImpl) ServiceFactory.getInstance().getService(ServiceType.RELATIONSHIP);
    private UserProfileController userProfileController;

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        try {
            boolean isSaved = relationshipService.save(
              new RelationshipDto(IdGenerator.generateId("Rel" ,5),
                      txtRelationship.getText(),
                      LoginController.userDto.getId())
            );

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Relationship Saved", ButtonType.OK).show();
                Stage stage = (Stage) txtRelationship.getScene().getWindow();
                stage.close();
                userProfileController.refreshRelationshipCombo();
                return;
            }

            new Alert(Alert.AlertType.INFORMATION, "Relationship cannot be saved", ButtonType.OK).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUserProfileController(UserProfileController userProfileController) {
        this.userProfileController = userProfileController;
    }
}
