package lk.ijse.gdse71.finalproject.jotit.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Mood {
    @FXML
    private ImageView image;

    @FXML
    private ToggleButton toggleMood;


    public void setLabel(String lbl){
        toggleMood.setText(lbl);
        String imagePath = "/view/assets/moodPics/" + lbl.toLowerCase() + ".png";
        Image moodImage;
        try {
            moodImage = new Image(getClass().getResourceAsStream(imagePath));
        }catch (Throwable e){
            imagePath = "/view/assets/moodPics/default.png";
            moodImage = new Image(getClass().getResourceAsStream(imagePath));
        }
        image.setImage(moodImage);
    }

    public boolean isSelected() {
        return toggleMood.isSelected();
    }
}
