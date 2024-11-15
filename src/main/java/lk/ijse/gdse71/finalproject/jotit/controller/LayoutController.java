package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

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
            Parent root = FXMLLoader.load(getClass().getResource("/view/addJot.fxml"));
            scrollPane.setContent(root);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void btnViewJotOnAction(ActionEvent event) {
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


}