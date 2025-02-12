package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lk.ijse.gdse71.finalproject.jotit.dto.CategoryDto;
import lk.ijse.gdse71.finalproject.jotit.dto.JotDto;
import lk.ijse.gdse71.finalproject.jotit.service.custom.CategoryService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.JotService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.CategoryServiceImpl;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.JotServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LayoutController {

    @FXML
    private Button btnViewJot;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField searchBar;
    @FXML
    private VBox categoryPane;

    @FXML
    private Label lblUserEmail;

    @FXML
    private Label lblUserName;

    private final CategoryService categoryService = new CategoryServiceImpl();
    private final JotService jotService = new JotServiceImpl();

    private List<Button> buttons = new ArrayList<>();
    private boolean isReadOnly;

    @FXML
    public void initialize() {
        searchBar.setVisible(true);
        lblUserEmail.setText(LoginController.userDto.getEmail());
        lblUserName.setText(LoginController.userDto.getUsername());
        Platform.runLater(() -> btnViewJot.requestFocus());
        loadCards();
        loadCategories();
    }

    public void loadCategories() {
        try {
            categoryPane.getChildren().clear();
            List<CategoryDto> categories = categoryService.getAllCategories(LoginController.userDto.getId());
            for (CategoryDto category : categories) {
                int jotCount = jotService.getJotCountByCategory(category.getId(), LoginController.userDto.getId());
                Button categoryButton = new Button(category.getDescription() + " (" + jotCount + ")");
                categoryButton.setPrefHeight(35);
                categoryButton.setPrefWidth(185);
                categoryButton.getStyleClass().add("category-button");

                categoryButton.setOnAction(event -> {
                    loadJotView("cat@" + category.getDescription());
                });
                buttons.add(categoryButton);
                categoryPane.getChildren().add(categoryButton);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading categories: " + e.getMessage(), ButtonType.OK).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddJotOnAction(ActionEvent event) {
        loadContentToPane("/view/addJot.fxml");
    }

    public <T>T loadContentToPane(String path) {
        try {
            searchBar.setVisible(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            scrollPane.setContent(root);
            return loader.getController();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        return null;
    }

    @FXML
    void btnViewJotOnAction(ActionEvent event) {
        searchBar.setVisible(true);
        loadCards();
    }

    private void loadCards() {
        loadJotView(null);
    }

    public void loadJotView(String searchText) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewJots.fxml"));
            Parent viewJotsRoot = loader.load();
            ViewJotsController viewJotsController = loader.getController();
            if (searchText != null) {
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

    @FXML
    void profileOnClicked(MouseEvent event) {
        loadContentToPane("/view/user/userprofile.fxml");
    }

    public void viewOnEdtitor(JotDto jotDto) {
        try {
            searchBar.setVisible(false);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addJot.fxml"));
            Parent root = loader.load();
            AddJotController addJotController = loader.getController();
            addJotController.loadJot(jotDto);

            if (isReadOnly){
                addJotController.setReadOnly(isReadOnly);
            }
            scrollPane.setContent(root);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to log out?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            try {
                Stage stage = (Stage) searchBar.getScene().getWindow();
                stage.close();

                LoginController.userDto = null;

                stage = new Stage();
                stage.setTitle("JoTtie");
                stage.setScene(new Scene(new FXMLLoader(getClass().getResource("/view/user/login.fxml")).load()));
                stage.show();
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/view/assets/logoPic.png")));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnReceivedOnAction(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/viewJots.fxml"));
            Parent viewJotsRoot = loader.load();
            ViewJotsController viewJotsController = loader.getController();
            viewJotsController.loadReceivedJots(LoginController.userDto.getId());
            scrollPane.setContent(viewJotsRoot);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error searching jots", ButtonType.OK).show();
        }
    }

    public void setReadOnly(boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }
}