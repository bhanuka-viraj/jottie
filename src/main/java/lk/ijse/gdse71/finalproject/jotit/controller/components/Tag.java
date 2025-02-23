package lk.ijse.gdse71.finalproject.jotit.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;
import lk.ijse.gdse71.finalproject.jotit.dto.TagDto;
import lk.ijse.gdse71.finalproject.jotit.service.ServiceFactory;
import lk.ijse.gdse71.finalproject.jotit.service.ServiceType;
import lk.ijse.gdse71.finalproject.jotit.service.custom.TagService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.TagServiceImpl;

public class Tag {
    @FXML
    private ToggleButton toggleTag;
    private final TagService tagService = (TagServiceImpl) ServiceFactory.getInstance().getService(ServiceType.TAG);
    private String loggedUserId;

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
            return tagService.getTag(toggleTag.getText(),loggedUserId);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            return null;
        }
    }

    public void setLoggedUser(String loggedUserId) {
        this.loggedUserId = loggedUserId;
    }
}