package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gdse71.finalproject.jotit.controller.components.Mood;
import lk.ijse.gdse71.finalproject.jotit.controller.components.Tag;
import lk.ijse.gdse71.finalproject.jotit.dto.CategoryDto;
import lk.ijse.gdse71.finalproject.jotit.dto.JotDto;
import lk.ijse.gdse71.finalproject.jotit.dto.LocationDto;
import lk.ijse.gdse71.finalproject.jotit.dto.MoodDto;
import lk.ijse.gdse71.finalproject.jotit.dto.TagDto;
import lk.ijse.gdse71.finalproject.jotit.model.CategoryModel;
import lk.ijse.gdse71.finalproject.jotit.model.JotModel;
import lk.ijse.gdse71.finalproject.jotit.model.LocationModel;
import lk.ijse.gdse71.finalproject.jotit.model.MoodModel;
import lk.ijse.gdse71.finalproject.jotit.model.TagModel;
import lk.ijse.gdse71.finalproject.jotit.model.impl.CategoryModelImpl;
import lk.ijse.gdse71.finalproject.jotit.model.impl.JotModelImpl;
import lk.ijse.gdse71.finalproject.jotit.model.impl.LocationModelImpl;
import lk.ijse.gdse71.finalproject.jotit.model.impl.MoodModelImpl;
import lk.ijse.gdse71.finalproject.jotit.model.impl.TagModelImpl;
import lk.ijse.gdse71.finalproject.jotit.util.IdGenerator;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddJotController {
    @FXML
    private ComboBox<LocationDto> locationCombo;

    @FXML
    private WebView webView;


    @FXML
    private TextField txtTitle;

    @FXML
    private GridPane moodGridPane;

    @FXML
    private FlowPane tagFlowPane;

    private WebEngine webEngine;
    private String currentFilePath = null;
    private final CategoryModel categoryModel = new CategoryModelImpl();
    private final LocationModel locationModel = new LocationModelImpl();
    private final MoodModel moodModel = new MoodModelImpl();
    private final TagModel tagModel = new TagModelImpl();
    private final JotModel jotModel = new JotModelImpl();

    private static final String SECRET_KEY = "1234567890123456";

    private CategoryDto selectedCategory;
    private ObservableList<MoodDto> selectedMoods = FXCollections.observableArrayList();
    private ObservableList<TagDto> selectedTags = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        webEngine = webView.getEngine();
        String editorFilePath = getClass().getResource("/view/editor/index.html").toExternalForm();
        webEngine.load(editorFilePath);

        try {
            refreshMoodList();
            refreshTagList();
            loadLocations();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getEditorContent() {
        return (String) webEngine.executeScript("getEditorContent();");
    }

    public void setEditorContent(String content) {
        webEngine.executeScript("setEditorContent('" + content.replace("'", "\\'") + "');");
    }

    @FXML
    void onSaveButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/category.fxml"));
            Parent categoryView = loader.load();

            CategoryController categoryController = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(categoryView));
            stage.setTitle("Select Category");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            loadView("/view/category.fxml", "Select category");

            stage.setOnHidden(e -> {
                selectedCategory = categoryController.getSelectedCategory();
                if (selectedCategory != null) {
                    try {
                        saveJotWithCategory();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

        } catch (IOException e) {

        }
    }

    @FXML
    void onLoadButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Note");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Encrypted Files", "*.enc"));
        fileChooser.setInitialDirectory(new File("jotsenc"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                byte[] encryptedContent = Files.readAllBytes(selectedFile.toPath());
                String decryptedContent = decrypt(encryptedContent, SECRET_KEY);
                setEditorContent(decryptedContent);
                currentFilePath = selectedFile.getAbsolutePath();
                showNotification("Note loaded successfully", "from:\n" + selectedFile.getAbsolutePath());
            } catch (Exception e) {
                showNotification("Failed to load note: ", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void saveJotWithCategory() throws Exception {
        collectSelectedMoods();
        collectSelectedTags();
        LocationDto selectedLocation = locationCombo.getValue();

        JotDto jotDto = new JotDto();
        jotDto.setId(IdGenerator.generateId("JT", 5));
        jotDto.setTitle(txtTitle.getText());
//        jotDto.setUserId("user001");
        jotDto.setPath(currentFilePath);
        jotDto.setCategory(selectedCategory);
        jotDto.setLocation(selectedLocation);
        jotDto.setMoods(selectedMoods);
        jotDto.setTags(selectedTags);

        saveFile();

        if (jotModel.saveJot(jotDto)) {
            showNotification("Note saved at: ", new File(currentFilePath).getAbsolutePath());
            currentFilePath = null;
            setEditorContent("");
        } else {
            showNotification("Failed to save the note.", "Please try again");
        }
    }

    private void saveFile() {
        try {
            String content = getEditorContent();
            if (currentFilePath == null) {
                String directoryPath = "jotsenc";
                Files.createDirectories(Paths.get(directoryPath));
                String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                currentFilePath = directoryPath + "/note_" + timestamp + ".enc";
            }

            byte[] encryptedContent = encrypt(content, SECRET_KEY);
            try (FileOutputStream fos = new FileOutputStream(currentFilePath)) {
                fos.write(encryptedContent);
            }


        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Failed to save the file.", ButtonType.OK).show();
            e.printStackTrace();
        }

    }

    private byte[] encrypt(String content, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(content.getBytes());
    }

    private String decrypt(byte[] encryptedContent, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedContent);
        return new String(decryptedBytes);
    }

    private void showNotification(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadView(String title, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent parent = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        }
    }
    @FXML
    void addLocationOnAction(ActionEvent event) {
        loadView("Add New Location", "/view/addLocation.fxml");
        try {
            loadLocations(); // Call loadLocations() directly after loading the view
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void addMoodOnAction(ActionEvent event) {
        loadView("Add New Mood", "/view/addMood.fxml");
        try {
            refreshMoodList(); // Call refreshMoodList() directly after loading the view
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void addTagOnAction(ActionEvent event) {
        loadView("Add New Tag", "/view/addTag.fxml");
        try {
            refreshTagList(); // Call refreshTagList() directly after loading the view
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void refreshMoodList() throws Exception {
        moodGridPane.getChildren().clear();

        List<MoodDto> moodDtos = moodModel.getAllMoods();

        int columnIndex = 0;
        int rowIndex = 0;

        for (MoodDto moodDto : moodDtos) {
            FXMLLoader moodLoader = new FXMLLoader(getClass().getResource("/view/components/mood.fxml"));
            Parent moodNode = moodLoader.load();

            Mood moodController = moodLoader.getController();
            moodController.setLabel(moodDto.getDescription());

            moodNode.getProperties().put("controller", moodController);
            moodGridPane.add(moodNode, columnIndex, rowIndex);

            columnIndex++;
            if (columnIndex == 2) {
                columnIndex = 0;
                rowIndex++;
            }
        }
    }

    private void refreshTagList() throws Exception {
        tagFlowPane.getChildren().clear(); // Clear existing tags

        List<TagDto> tagDtos = tagModel.getAllTags(); // Fetch all tags

        for (TagDto tagDto : tagDtos) {
            System.out.println(tagDto);
            FXMLLoader tagLoader = new FXMLLoader(getClass().getResource("/view/components/tag.fxml")); // Assuming you have a tag.fxml
            Parent tagNode = tagLoader.load();

            Tag tagController = tagLoader.getController();
            tagController.setLabel(tagDto.getName());

            tagNode.getProperties().put("controller", tagController);
            FlowPane.setMargin(tagNode, new Insets(2, 2, 2, 2));
            tagFlowPane.getChildren().add(tagNode);
        }
    }

    private void loadLocations() {
        try {
            List<LocationDto> locationDtos = locationModel.getAllLocations();
            ObservableList<LocationDto> observableList = FXCollections.observableArrayList(locationDtos);
            locationCombo.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load locations").show();
        }
    }

    private void collectSelectedMoods() {
        selectedMoods.clear();
        for (Node node : moodGridPane.getChildren()) {
            if (node instanceof Parent root) {
                Mood moodController = (Mood) root.getProperties().get("controller");
                if (moodController != null && moodController.isSelected()) {
                    MoodDto moodDto = moodController.getSelectedMoodDto();
                    selectedMoods.add(moodDto);
                }
            }
        }
    }

    private void collectSelectedTags() {
        selectedTags.clear();
        //tags and moods are not collecting
        for (Node node : tagFlowPane.getChildren()) {
            if (node instanceof Parent root) {
                Tag tagController = (Tag) root.getProperties().get("controller");
                if (tagController != null && tagController.isSelected()) {
                    TagDto tagDto = tagController.getSelectedTagDto();
                    System.out.println(tagDto.getName());
                    selectedTags.add(tagDto);
                }
            }
        }
    }

}