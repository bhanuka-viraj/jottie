package lk.ijse.gdse71.finalproject.jotit.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.gdse71.finalproject.jotit.dto.MoodDto;

public class CardMood {

    @FXML
    private ImageView imaageMood;

    @FXML
    private Label lblMood;

    public void setData(MoodDto moodDto){
        lblMood.setText(moodDto.getDescription());

        String imagePath = "/view/assets/moodPics/" + moodDto.getDescription().toLowerCase() + ".png";
        Image moodImage;
        try {
            moodImage = new Image(getClass().getResourceAsStream(imagePath));
        }catch (Throwable e){
            imagePath = "/view/assets/moodPics/default.png";
            moodImage = new Image(getClass().getResourceAsStream(imagePath));
        }
        imaageMood.setImage(moodImage);
    }
}
