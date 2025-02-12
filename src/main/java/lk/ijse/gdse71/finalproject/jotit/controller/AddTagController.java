package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.gdse71.finalproject.jotit.dto.TagDto;
import lk.ijse.gdse71.finalproject.jotit.service.custom.TagService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.TagServiceImpl;
import lk.ijse.gdse71.finalproject.jotit.util.IdGenerator;

public class AddTagController {

    @FXML
    private TextField txtTag;

    private final TagService tagService = new TagServiceImpl();

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            boolean isSaved = tagService.saveTag(new TagDto(
                    IdGenerator.generateId("TG",5),
                    txtTag.getText(),
                    LoginController.userDto.getId()
            ));

            if (isSaved) {
                txtTag.setText("");
                txtTag.getScene().getWindow().hide();
                ControllerRef.addJotController.refreshTagList();
                new Alert(Alert.AlertType.INFORMATION, "Tag Saved").show();

            }else {
                new Alert(Alert.AlertType.ERROR, "Error saving Tag").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING,e.getMessage()).show();
        }

    }

}
