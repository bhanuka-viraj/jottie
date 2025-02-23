package lk.ijse.gdse71.finalproject.jotit.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.gdse71.finalproject.jotit.dto.MoodDto;
import lk.ijse.gdse71.finalproject.jotit.service.ServiceFactory;
import lk.ijse.gdse71.finalproject.jotit.service.ServiceType;
import lk.ijse.gdse71.finalproject.jotit.service.custom.MoodService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.MoodServiceImpl;

public class Mood {
    @FXML
    private ImageView image;

    @FXML
    private ToggleButton toggleMood;

    private final MoodService moodService = (MoodServiceImpl) ServiceFactory.getInstance().getService(ServiceType.MOOD);


    public void setData(MoodDto moodDto){
        toggleMood.setText(moodDto.getDescription());
        String imagePath = "/view/assets/moodPics/" + moodDto.getDescription().toLowerCase() + ".png";
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
    public void setSelected(boolean selected) {
        System.out.println("mood selected: " + selected);
        toggleMood.setSelected(selected);
    }

    public MoodDto getSelectedMoodDto(){
        try {
            MoodDto moodDto = moodService.getMood(toggleMood.getText());
            return moodDto;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            return null;
        }

    }

}