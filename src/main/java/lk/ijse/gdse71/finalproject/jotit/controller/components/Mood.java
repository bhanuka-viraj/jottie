package lk.ijse.gdse71.finalproject.jotit.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Mood {
    @FXML
    private ImageView image;

    @FXML
    Label lblMood;


    public void setLabel(String lbl){
        lblMood.setText(lbl);
        // Load the corresponding image based on the label
        // Load the corresponding image based on the label
        String imagePath = "/view/assets/moodPics/" + lbl.toLowerCase() + ".png";
        System.out.println(imagePath);
        Image moodImage;
        try {
            moodImage = new Image(getClass().getResourceAsStream(imagePath));
        }catch (Throwable e){
            imagePath = "/view/assets/moodPics/default.png";
            moodImage = new Image(getClass().getResourceAsStream(imagePath));
        }

        image.setImage(moodImage);

    }
}
