package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.gdse71.finalproject.jotit.dto.CategoryDto;
import lk.ijse.gdse71.finalproject.jotit.model.CategoryModel;
import lk.ijse.gdse71.finalproject.jotit.model.impl.CategoryModelImpl;
import lk.ijse.gdse71.finalproject.jotit.util.IdGenerator;

import java.util.List;

public class CategoryController {


    @FXML
    private Button btnSaveCat;

    @FXML
    private ComboBox<CategoryDto> categoryCombo;

    @FXML
    private TextField txtCategoryDescription;


    private CategoryModel categoryModel;
    private CategoryDto selectedCategory; // To store the selected category

    @FXML
    public void initialize() {
        categoryModel = new CategoryModelImpl();
        loadCategories();
        btnSaveCat.setVisible(false);
        txtCategoryDescription.setVisible(false);
    }

    @FXML
    void addCategoryOnAction(ActionEvent event) {
        btnSaveCat.setVisible(true);
        txtCategoryDescription.setVisible(true);
    }


        private void loadCategories() {
        try {
            List<CategoryDto> categoryDtos = categoryModel.getAllCategories();
            ObservableList<CategoryDto> observableList = FXCollections.observableArrayList(categoryDtos);
            categoryCombo.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load categories " + e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveCategoryOnAction(ActionEvent event) {
        try {
            String categoryDescription = txtCategoryDescription.getText();
            if (categoryDescription.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter a category description").show();
                return;
            }

            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(IdGenerator.generateId("Cat",5));
            categoryDto.setDescription(categoryDescription);

            if (categoryModel.saveCategory(categoryDto)) {
                new Alert(Alert.AlertType.INFORMATION, "Category saved successfully").show();
                loadCategories(); // Refresh the categoryCombo
                txtCategoryDescription.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save category").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveJotOnAction(ActionEvent event) {
        selectedCategory = categoryCombo.getValue();
        if (selectedCategory == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a category").show();
            return;
        }

        // Close the category window
        Stage stage = (Stage) categoryCombo.getScene().getWindow();
        stage.close();
    }

    // Method to get the selected category
    public CategoryDto getSelectedCategory() {
        return selectedCategory;
    }
}