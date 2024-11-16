package lk.ijse.gdse71.finalproject.jotit.controller.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import lk.ijse.gdse71.finalproject.jotit.controller.AddJotController;
import lk.ijse.gdse71.finalproject.jotit.controller.Controller;
import lk.ijse.gdse71.finalproject.jotit.dto.JotDto;
import lk.ijse.gdse71.finalproject.jotit.dto.MoodDto;
import lk.ijse.gdse71.finalproject.jotit.dto.TagDto;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CardJot {

    @FXML
    private Label lblCategory;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblLocation;

    @FXML
    private Label lblTitle;

    @FXML
    private HBox moodHbox;

    @FXML
    private HBox tagHbox;
    @FXML
    private AnchorPane pane;
    @FXML
    private PieChart pieChart;
    @FXML
    private Label lblTasks;

    private JotDto jotDto;

    @FXML
    void cardClickOnAction(MouseEvent event) {

    }

    @FXML
    void openOnClicked(MouseEvent event) {
        if (jotDto != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addJot.fxml"));
                Parent root = loader.load();

                Controller.layoutController.viewOnEdtitor(jotDto);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setData(JotDto jotDto){
        this.jotDto = jotDto;
        lblCategory.setText(jotDto.getCategory().getDescription());
        lblLocation.setText(jotDto.getLocation().getDescription());
        lblTitle.setText(jotDto.getTitle());

        Date date = jotDto.getUpdatedAt();
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        PrettyTime p = new PrettyTime();

        lblDate.setText(p.format(localDateTime));

        for (MoodDto mood : jotDto.getMoods()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/card_mood.fxml"));
                Parent cardMoodRoot = loader.load();
                CardMood cardMoodController = loader.getController();
                cardMoodController.setData(mood);
                moodHbox.getChildren().add(cardMoodRoot);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (TagDto tag : jotDto.getTags()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/card_tag.fxml"));
                Parent cardTagRoot = loader.load();
                CardTag cardTagController = loader.getController();
                cardTagController.setData(tag);
                tagHbox.getChildren().add(cardTagRoot);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void btnAddTaskOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }


}