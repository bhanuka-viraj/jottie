package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.gdse71.finalproject.jotit.dto.LocationDto;
import lk.ijse.gdse71.finalproject.jotit.service.custom.LocationService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.LocationServiceImpl;
import lk.ijse.gdse71.finalproject.jotit.util.IdGenerator;

public class AddLocationController {

    @FXML
    private TextField txtLocation;

    private LocationService locationService = new LocationServiceImpl();

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            boolean isSaved = locationService.saveLocation(new LocationDto(
                    IdGenerator.generateId("LC",5),
                    txtLocation.getText(),
                    LoginController.userDto.getId()
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