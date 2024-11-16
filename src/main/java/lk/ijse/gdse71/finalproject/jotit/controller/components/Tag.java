package lk.ijse.gdse71.finalproject.jotit.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import lk.ijse.gdse71.finalproject.jotit.dto.TagDto;
import lk.ijse.gdse71.finalproject.jotit.model.TagModel;
import lk.ijse.gdse71.finalproject.jotit.model.impl.TagModelImpl;

public class Tag {
    @FXML
    private ToggleButton toggleTag;
    private final TagModel tagModel = new TagModelImpl();

    public void setData(TagDto tagDto) {
        toggleTag.setText(tagDto.getName());
    }

    public boolean isSelected() {
        return toggleTag.isSelected();
    }

    public void setSelected(boolean selected) {
        toggleTag.setSelected(selected);
    }

    public TagDto getSelectedTagDto(){
        try {
            return tagModel.getTag(toggleTag.getText());
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            return null;
        }
    }
}