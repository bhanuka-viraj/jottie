package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import lk.ijse.gdse71.finalproject.jotit.controller.components.CardJot;
import lk.ijse.gdse71.finalproject.jotit.dto.JotDto;
import lk.ijse.gdse71.finalproject.jotit.dto.SharedJotDto;
import lk.ijse.gdse71.finalproject.jotit.model.JotModel;
import lk.ijse.gdse71.finalproject.jotit.model.SharedJotModel;
import lk.ijse.gdse71.finalproject.jotit.model.impl.JotModelImpl;
import lk.ijse.gdse71.finalproject.jotit.model.impl.SharedJotModelImpl;

import java.util.ArrayList;
import java.util.List;

public class ViewJotsController {
    @FXML
    private GridPane gridPane;

    private final JotModel jotModel = new JotModelImpl();
    private final SharedJotModel sharedJotModel = new SharedJotModelImpl();
    private boolean isReceiving;


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
                jots = jotModel.getAllJot(LoginController.userDto.getId());
            }

            int row = 0;
            int col = 0;
            gridPane.getChildren().clear();
            for (JotDto jot : jots) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/_cardjot.fxml"));
                Parent jotCardRoot = loader.load();
                CardJot jotCardController = loader.getController();
                jotCardController.setLoggedUser(LoginController.userDto.getId());

                if (isReceiving){
                    jotCardController.setReadOnly(isReceiving);
                }

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
            new Alert(Alert.AlertType.ERROR, "Something went wrong" , ButtonType.OK).show();
        }

    }

    public void setSearchResults(String searchText) {
        try {
            List<JotDto> jots = null;
            if (searchText != null && !searchText.isEmpty()) {
                jots = jotModel.findJots(searchText,LoginController.userDto.getId());
            }
            load(jots);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong" , ButtonType.OK).show();
        }
    }

    public void loadReceivedJots(String userId) {
        try {
            List<SharedJotDto> sharedJots = sharedJotModel.getAllByReceiverId(userId);

            List<String> jotIds = sharedJots.stream()
                    .map(SharedJotDto::getJotId)
                    .toList();

            List<JotDto> receivedJots = new ArrayList<>();
            for (String jotId : jotIds) {
                JotDto jotDto = jotModel.getJot(jotId);
                if (jotDto != null) {
                    receivedJots.add(jotDto);
                }
            }
            isReceiving = true;
            load(receivedJots);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }
    }
}
