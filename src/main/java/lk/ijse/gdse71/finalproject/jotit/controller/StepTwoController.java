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
import lk.ijse.gdse71.finalproject.jotit.service.ServiceFactory;
import lk.ijse.gdse71.finalproject.jotit.service.ServiceType;
import lk.ijse.gdse71.finalproject.jotit.service.custom.CategoryService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.CategoryServiceImpl;
import lk.ijse.gdse71.finalproject.jotit.util.IdGenerator;

import java.util.List;

public class StepTwoController {


    @FXML
    private Button btnSaveCat;

    @FXML
    private ComboBox<CategoryDto> categoryCombo;

    @FXML
    private TextField txtCategoryDescription;

    private AddJotController addJotController;
    private CategoryService categoryService;
    private CategoryDto selectedCategory;
    @FXML
    public void initialize() {
        categoryService = (CategoryServiceImpl) ServiceFactory.getInstance().getService(ServiceType.CATEGORY);
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
            List<CategoryDto> categoryDtos = categoryService.getAllCategories(LoginController.userDto.getId());
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
            categoryDto.setId(IdGenerator.generateId("Cat", 5));
            categoryDto.setDescription(categoryDescription);
            categoryDto.setUserId(LoginController.userDto.getId());

            if (categoryService.saveCategory(categoryDto)) {
                new Alert(Alert.AlertType.INFORMATION, "Category saved successfully").show();
                loadCategories();
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
;
        Stage stage = (Stage) categoryCombo.getScene().getWindow();
        stage.close();
    }

    public CategoryDto getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(CategoryDto selectedCategory) {
        categoryCombo.setValue(selectedCategory);
        this.selectedCategory = selectedCategory;
    }

    public void setAddJotController(AddJotController addJotController) {
        this.addJotController = addJotController;
    }

}