package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import lk.ijse.gdse71.finalproject.jotit.controller.components.CardJot;
import lk.ijse.gdse71.finalproject.jotit.dto.JotDto;
import lk.ijse.gdse71.finalproject.jotit.model.JotModel;
import lk.ijse.gdse71.finalproject.jotit.model.impl.JotModelImpl;

import java.util.List;

public class ViewJotsController {
    @FXML
    private GridPane gridPane;

    private final JotModel jotModel = new JotModelImpl();
    private String userId;

    public void initialize() {
        loadJotCards();
    }

    private void loadJotCards() {
        try {
            List<JotDto> jots = jotModel.getAllJot(userId);
            int row = 0;
            int col = 0;
            for (JotDto jot : jots) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/card_jot_.fxml"));
                Parent jotCardRoot = loader.load();
                CardJot jotCardController = loader.getController();
                jotCardController.setData(jot);

                gridPane.add(jotCardRoot, col, row); // Add to GridPane

                col++;
                if (col == 4) { // Assuming 2 columns
                    col = 0;
                    row++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
