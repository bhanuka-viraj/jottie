package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import lk.ijse.gdse71.finalproject.jotit.dto.JotDto;

import java.io.IOException;

public class LayoutController {

    @FXML
    private Button btnAddJot;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField searchBar;

    @FXML
    public void initialize() {

    }


    @FXML
    void btnAddJotOnAction(ActionEvent event) {
        try {
            searchBar.setVisible(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addJot.fxml"));
            Parent root = loader.load();
            AddJotController addJotController = loader.getController();
            scrollPane.setContent(root);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void btnViewJotOnAction(ActionEvent event) {
        searchBar.setVisible(true);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewJots.fxml"));
            Parent viewJotsRoot = loader.load();
            ViewJotsController viewJotsController = loader.getController(); // Get the controller

            // Assuming you have a method in ViewJotsController to set the userId
            viewJotsController.setUserId("userid should be passed"); // Set the userId

            scrollPane.setContent(viewJotsRoot);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    public void viewOnEdtitor(JotDto jotDto) {
        try {
            searchBar.setVisible(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addJot.fxml"));
            Parent root = loader.load();
            AddJotController addJotController = loader.getController();
            addJotController.loadJot(jotDto);

            scrollPane.setContent(root);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

}