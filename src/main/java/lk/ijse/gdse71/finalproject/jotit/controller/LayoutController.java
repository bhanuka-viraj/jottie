package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import lk.ijse.gdse71.finalproject.jotit.dto.CategoryDto;
import lk.ijse.gdse71.finalproject.jotit.dto.JotDto;
import lk.ijse.gdse71.finalproject.jotit.model.CategoryModel;
import lk.ijse.gdse71.finalproject.jotit.model.JotModel;
import lk.ijse.gdse71.finalproject.jotit.model.impl.CategoryModelImpl;
import lk.ijse.gdse71.finalproject.jotit.model.impl.JotModelImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LayoutController {

    @FXML
    private Button btnAddJot;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField searchBar;
    @FXML
    private VBox categoryPane;

    private final CategoryModel categoryModel= new CategoryModelImpl();
    private final JotModel jotModel= new JotModelImpl();

    private List<Button> buttons = new ArrayList<>();

    @FXML
    public void initialize() {

        loadCategories();
    }

    public void loadCategories() {
        try {

            categoryPane.getChildren().clear();
            List<CategoryDto> categories = categoryModel.getAllCategories();
            for (CategoryDto category : categories) {
                int jotCount = jotModel.getJotCountByCategory(category.getId());
                Button categoryButton = new Button(category.getDescription() + " (" + jotCount + ")");
                categoryButton.setPrefHeight(35);
                categoryButton.setPrefWidth(185);
                categoryButton.setStyle("-fx-background-color: rgb(180,13,204);-fx-text-fill: white;-fx-font-style: Arial-black");


                categoryButton.setOnAction(event -> {
                    loadJotView("cat@"+category.getDescription());
                });
                buttons.add(categoryButton);

                categoryPane.getChildren().add(categoryButton);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading categories: " + e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void btnAddJotOnAction(ActionEvent event) {
        try {
            searchBar.setVisible(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addJot.fxml"));
            Parent root = loader.load();
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

        try{
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