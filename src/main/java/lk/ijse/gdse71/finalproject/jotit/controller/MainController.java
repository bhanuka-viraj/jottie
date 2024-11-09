package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML
    private TreeView<String> treeMenu;

    @FXML
    private StackPane stackPane;

    TreeItem<String> root;

    @FXML
    public void initialize() {
        // Create the root TreeItem
        root = new TreeItem<>("Root");

        addTreeMenuItems("Dashboard", "Add New Jot", "View Jots");
        addTreeMenuItems("Moods", "Add a Mood", "View Jots");
        addTreeMenuItems("Tasks", "Add a Task", "View Jots");
        addTreeMenuItems("Categories", "Add a Category");
        addTreeMenuItems("Received", "View Received Jots", "View Jots");
        addTreeMenuItems("Tags", "Add a Tag");
        addTreeMenuItems("My Jots", "Add a My Jots", "View Jots");
        addTreeMenuItems("My Jots", "Add a My Jots", "View Jots");

        treeMenu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedItem = newValue.getValue();
                handleClick(selectedItem);
            }
        });
    }

    private void addTreeMenuItems(String menuItem, String... subItems) {
        TreeItem<String> mainItem = new TreeItem<>(menuItem);
        for (String subItem : subItems) {
            mainItem.getChildren().add(new TreeItem<>(subItem));
        }
        root.getChildren().add(mainItem);
        treeMenu.setRoot(root);
        treeMenu.setShowRoot(false);
    }

    private void handleClick(String selectedItem) {
        if (!selectedItem.isEmpty()) {
            stackPane.getChildren().clear();
            selectedItem = selectedItem.toLowerCase();
            selectedItem = selectedItem.replaceAll(" ", "_");
            String path = "/view/" + selectedItem + ".fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            try {
                stackPane.getChildren().add(loader.load());
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (IllegalStateException e) {
//                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        }

    }


}