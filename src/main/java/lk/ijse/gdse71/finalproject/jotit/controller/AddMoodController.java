package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.gdse71.finalproject.jotit.dto.MoodDto;
import lk.ijse.gdse71.finalproject.jotit.model.MoodModel;
import lk.ijse.gdse71.finalproject.jotit.model.impl.MoodModelImpl;

public class AddMoodController {

    @FXML
    private TextField txtMood;

    private final MoodModel moodModel = new MoodModelImpl();

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            moodModel.saveMood(new MoodDto());
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING,e.getMessage()).show();
        }
    }
}
