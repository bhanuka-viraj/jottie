package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.gdse71.finalproject.jotit.dto.MoodDto;
import lk.ijse.gdse71.finalproject.jotit.model.MoodModel;
import lk.ijse.gdse71.finalproject.jotit.model.impl.MoodModelImpl;
import lk.ijse.gdse71.finalproject.jotit.util.IdGenerator;

public class AddMoodController {

    @FXML
    private TextField txtMood;

    private final MoodModel moodModel = new MoodModelImpl();

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            boolean isSaved = moodModel.saveMood(new MoodDto(
                    IdGenerator.generateId("MD",5),
                    txtMood.getText()
            ));

            if (isSaved) {
                txtMood.setText("");
                txtMood.getScene().getWindow().hide();
                ControllerRef.addJotController.refreshMoodList();
                new Alert(Alert.AlertType.INFORMATION, "Mood Saved").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Error saving mood").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING,e.getMessage()).show();
        }
    }
}
