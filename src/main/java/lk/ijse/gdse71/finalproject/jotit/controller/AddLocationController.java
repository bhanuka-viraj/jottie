package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.gdse71.finalproject.jotit.dto.LocationDto;
import lk.ijse.gdse71.finalproject.jotit.dto.MoodDto;
import lk.ijse.gdse71.finalproject.jotit.model.LocationModel;
import lk.ijse.gdse71.finalproject.jotit.model.impl.LocationModelImpl;
import lk.ijse.gdse71.finalproject.jotit.util.IdGenerator;

public class AddLocationController {

    @FXML
    private TextField txtLocation;

    private LocationModel locationModel = new LocationModelImpl();

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            boolean isSaved = locationModel.saveLocation(new LocationDto(
                    IdGenerator.generateId("LC",5),
                    txtLocation.getText()
            ));

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Location Saved").show();
                ControllerRef.addJotController.refreshLocationCombo();
                txtLocation.getScene().getWindow().hide();
            }else {
                new Alert(Alert.AlertType.ERROR, "Error saving Location").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING,e.getMessage()).show();
        }
    }

}