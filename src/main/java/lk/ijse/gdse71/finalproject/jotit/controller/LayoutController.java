package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
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
        loadCards();
    }

    private void loadCards() {
        loadJotView(null);
    }

    public void loadJotView(String searchText){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewJots.fxml"));
            Parent viewJotsRoot = loader.load();
            if (searchText!=null){
                ViewJotsController viewJotsController = loader.getController();

                viewJotsController.setSearchResults(searchText);
            }
            scrollPane.setContent(viewJotsRoot);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error searching jots", ButtonType.OK).show();
        }
    }

    @FXML
    void txtSearchTyped(KeyEvent event) {
        String searchText = searchBar.getText();

        Task<Void> searchTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(1000); // 2-second delay
                return null;
            }
        };

        searchTask.setOnSucceeded(e -> Platform.runLater(() -> loadJotView(searchText)));

        new Thread(searchTask).start();
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