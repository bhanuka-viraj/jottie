package lk.ijse.gdse71.finalproject.jotit.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Mood {
    @FXML
    private ImageView image;

    @FXML
    Label lblMood;


    public void setLabel(String lbl){
        lblMood.setText(lbl);
    }
}
