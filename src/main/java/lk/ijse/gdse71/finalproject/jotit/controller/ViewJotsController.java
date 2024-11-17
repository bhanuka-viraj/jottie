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
        ControllerRef.viewJotsController = this;
        loadJotCards();
    }

    public void loadJotCards() {
        load(null);
    }

    public void load(List<JotDto> jots) {
        try {
            if (jots == null) {
                jots = jotModel.getAllJot(userId);
            }

            int row = 0;
            int col = 0;
            for (JotDto jot : jots) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/card_jot_.fxml"));
                Parent jotCardRoot = loader.load();
                CardJot jotCardController = loader.getController();
                jotCardController.setData(jot);

                gridPane.add(jotCardRoot, col, row);
                col++;
                if (col == 4) {
                    col = 0;
                    row++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setSearchResults(String title) {
        try {
            load(jotModel.findJots(title));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
