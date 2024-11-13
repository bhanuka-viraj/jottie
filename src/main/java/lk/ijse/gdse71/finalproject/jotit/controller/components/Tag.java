package lk.ijse.gdse71.finalproject.jotit.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

public class Tag {
    @FXML
    private ToggleButton toggleTag; // Changed to ToggleButton

    public void setLabel(String label) {
        toggleTag.setText(label);
    }

    public boolean isSelected() {
        return toggleTag.isSelected();
    }
}