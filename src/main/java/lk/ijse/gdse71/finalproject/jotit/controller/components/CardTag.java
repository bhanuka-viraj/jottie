package lk.ijse.gdse71.finalproject.jotit.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lk.ijse.gdse71.finalproject.jotit.dto.MoodDto;
import lk.ijse.gdse71.finalproject.jotit.dto.TagDto;

public class CardTag {

    @FXML
    private Label lblTag;

    public void setData(TagDto tag) {
        lblTag.setText(tag.getName());
    }
}
