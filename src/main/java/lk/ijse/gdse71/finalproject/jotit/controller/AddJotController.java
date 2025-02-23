package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gdse71.finalproject.jotit.controller.components.Mood;
import lk.ijse.gdse71.finalproject.jotit.controller.components.Tag;
import lk.ijse.gdse71.finalproject.jotit.dao.DaoFactory;
import lk.ijse.gdse71.finalproject.jotit.dao.DaoType;
import lk.ijse.gdse71.finalproject.jotit.dto.CategoryDto;
import lk.ijse.gdse71.finalproject.jotit.dto.JotDto;
import lk.ijse.gdse71.finalproject.jotit.dto.LocationDto;
import lk.ijse.gdse71.finalproject.jotit.dto.MoodDto;
import lk.ijse.gdse71.finalproject.jotit.dto.TagDto;
import lk.ijse.gdse71.finalproject.jotit.entity.Category;
import lk.ijse.gdse71.finalproject.jotit.service.custom.*;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.*;
import lk.ijse.gdse71.finalproject.jotit.util.IdGenerator;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
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
    @FXML
    private Button addLocation;

    @FXML
    private Button addMood;

    @FXML
    private Button addTag;

    @FXML
    private Button btnSave;

    private WebEngine webEngine;
    private String currentFilePath = null;
    private final CategoryService categoryService = (CategoryService) DaoFactory.getInstance().getDao(DaoType.CATEGORY);
    private final LocationService locationService = (LocationService) DaoFactory.getInstance().getDao(DaoType.LOCATION);
    private final MoodService moodService = (MoodService) DaoFactory.getInstance().getDao(DaoType.MOOD);
    private final TagService tagService = (TagService) DaoFactory.getInstance().getDao(DaoType.TAG);
    private final JotService jotService = (JotService) DaoFactory.getInstance().getDao(DaoType.JOT);

    private static final String SECRET_KEY = "1234567890123456";

    private CategoryDto selectedCategory;
    private ObservableList<MoodDto> selectedMoods = FXCollections.observableArrayList();
    private ObservableList<TagDto> selectedTags = FXCollections.observableArrayList();
    private JotDto jotDto;

    @FXML
    public void initialize() {
        ControllerRef.addJotController = this;
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

            if (saveJotWithCategory()){
                clear();
                ControllerRef.layoutController.loadCategories();
            }
    }

    private boolean setCategory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/step_two.fxml"));
            Parent categoryView = loader.load();

            StepTwoController stepTwoController = loader.getController();

            if (this.jotDto != null) {
                stepTwoController.setSelectedCategory(jotDto.getCategory());
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(categoryView));
            stage.setTitle("Select Category");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            stage.setOnCloseRequest(windowEvent -> {
                stepTwoController.setSelectedCategory(null);
            });

            stage.showAndWait();

            stepTwoController.setAddJotController(this);

            if (stepTwoController.getSelectedCategory() != null) {
                selectedCategory = stepTwoController.getSelectedCategory();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void clear() {
        txtTitle.setText("");
        locationCombo.setValue(null);
        setEditorContent("");
        selectedMoods.clear();
        selectedTags.clear();

        for (Node node : moodGridPane.getChildren()) {
            if (node instanceof Parent root) {
                Mood moodController = (Mood) root.getProperties().get("controller");
                if (moodController != null) {
                    moodController.setSelected(false);
                }
            }
        }
        for (Node node : tagFlowPane.getChildren()) {
            if (node instanceof Parent root) {
                Tag tagController = (Tag) root.getProperties().get("controller");
                if (tagController != null) {
                    tagController.setSelected(false);
                }
            }
        }
    }

    public void loadJot(JotDto jotDto) {
        if (jotDto == null) {
            System.err.println("Error: JotDto is null.");
            return;
        }

        String relativePath = jotDto.getPath();
        if (relativePath == null || relativePath.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Error: Path in JotDto is null or empty.", ButtonType.OK).show();
            return;
        }

        String baseDir = System.getProperty("user.dir");

        Path jotPath = Paths.get(baseDir, relativePath);
        try {
            if (!Files.exists(jotPath)) {
                System.err.println("Error: File does not exist at path: " + jotPath);
                return;
            }
            byte[] encryptedContent = Files.readAllBytes(jotPath);
            String decryptedContent = decrypt(encryptedContent, SECRET_KEY);

            webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == Worker.State.SUCCEEDED) {
                    setEditorContent(decryptedContent);
                }
            });
            setJotData(jotDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setJotData(JotDto jotDto) {
        this.jotDto = jotDto;
        refreshMoodList();
        refreshTagList();
        txtTitle.setText(jotDto.getTitle());
        locationCombo.setValue(jotDto.getLocation());
    }

    private boolean saveJotWithCategory() {
        try {
            String title = txtTitle.getText();
            LocationDto location = locationCombo.getValue();
            String content = getEditorContent();

            if (title == null || title.trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter a title for the jot.", ButtonType.OK).show();
                return false;
            }

            if (location == null) {
                new Alert(Alert.AlertType.WARNING, "Please select a location.", ButtonType.OK).show();
                return false;
            }

            if (content == null || content.trim().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "The jot content cannot be empty.", ButtonType.OK).show();
                return false;
            }

            collectSelectedMoods();
            collectSelectedTags();

            if (selectedMoods.isEmpty() && selectedTags.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please select at least one mood or tag.", ButtonType.OK).show();
                return false;
            }

            if (setCategory()) {
                if (saveFile()) {
                    JotDto jotDto = new JotDto();
                    if (this.jotDto == null) {
                        jotDto.setId(IdGenerator.generateId("JT", 5));
                    } else {
                        jotDto.setId(this.jotDto.getId());
                    }
                    jotDto.setTitle(title);
                    jotDto.setCategory(selectedCategory);
                    jotDto.setLocation(location);
                    jotDto.setMoods(selectedMoods);
                    jotDto.setTags(selectedTags);
                    jotDto.setUserId(LoginController.userDto.getId());
                    jotDto.setPath(currentFilePath);

                    if (jotService.saveJot(jotDto)) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Note saved successfully..", ButtonType.OK).show();
                        currentFilePath = null;
                        setEditorContent("");
                        this.jotDto = null;
                        return true;
                    } else {
                        showNotification("Failed to save the note.", "Please try again");
                        return false;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage(), ButtonType.OK).show();
            return false;
        }

    }
    private boolean saveFile() {
        try {
            String content = getEditorContent();
            if (currentFilePath == null) {
                String directoryPath = "jotsenc";
                Files.createDirectories(Paths.get(directoryPath));
                String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                this.currentFilePath = directoryPath + "/note_" + timestamp + ".enc";
            }

            byte[] encryptedContent = encrypt(content, SECRET_KEY);
            try (FileOutputStream fos = new FileOutputStream(currentFilePath)) {
                fos.write(encryptedContent);
                return true;
            }


        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the file.", ButtonType.OK).show();
            e.printStackTrace();
            return false;
        }

    }

    //need to learn more
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
            loadLocations();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void addMoodOnAction(ActionEvent event) {
        loadView("Add New Mood", "/view/addMood.fxml");
        try {
            refreshMoodList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void addTagOnAction(ActionEvent event) {
        loadView("Add New Tag", "/view/addTag.fxml");
        try {
            refreshTagList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void refreshMoodList() {
        try {
            moodGridPane.getChildren().clear();

            List<MoodDto> moodDtos = moodService.getAllMoods();

            int columnIndex = 0;
            int rowIndex = 0;

            for (MoodDto moodDto : moodDtos) {
                FXMLLoader moodLoader = new FXMLLoader(getClass().getResource("/view/components/mood.fxml"));
                Parent moodNode = moodLoader.load();

                Mood moodController = moodLoader.getController();
                moodController.setData(moodDto);

                if (this.jotDto != null) {
                    List<MoodDto> moods = this.jotDto.getMoods();

                    for (MoodDto moodDto2 : moods) {
                        if (moodDto2.getId().equals(moodDto.getId())) {
                            moodController.setSelected(true);
                        }
                    }
                }

                moodNode.getProperties().put("controller", moodController);
                moodGridPane.add(moodNode, columnIndex, rowIndex);

                columnIndex++;
                if (columnIndex == 2) {
                    columnIndex = 0;
                    rowIndex++;
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK).show();
        }

    }

    public void refreshTagList() {
        try {
            tagFlowPane.getChildren().clear();

            List<TagDto> tagDtos = tagService.getAllTags(LoginController.userDto.getId());

            for (TagDto tagDto : tagDtos) {
                System.out.println(tagDto);
                FXMLLoader tagLoader = new FXMLLoader(getClass().getResource("/view/components/tag.fxml"));
                Parent tagNode = tagLoader.load();

                Tag tagController = tagLoader.getController();
                tagController.setData(tagDto);

                if (this.jotDto != null) {
                    List<TagDto> tags = this.jotDto.getTags();
                    for (TagDto tagDto2 : tags) {
                        if (tagDto2.getId().equals(tagDto.getId())) {
                            tagController.setSelected(true);
                        }
                    }
                }
                tagNode.getProperties().put("controller", tagController);
                FlowPane.setMargin(tagNode, new Insets(2, 2, 2, 2));
                tagFlowPane.getChildren().add(tagNode);
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK).show();
        }
    }

    private void loadLocations() {
        try {
            List<LocationDto> locationDtos = locationService.getAllLocations(LoginController.userDto.getId());
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
        for (Node node : tagFlowPane.getChildren()) {
            if (node instanceof Parent root) {
                Tag tagController = (Tag) root.getProperties().get("controller");
                if (tagController != null && tagController.isSelected()) {
                    tagController.setLoggedUser(LoginController.userDto.getId());
                    TagDto tagDto = tagController.getSelectedTagDto();
                    System.out.println(tagDto.getName());
                    selectedTags.add(tagDto);
                }
            }
        }
    }

    public void setSelectedCategory(CategoryDto selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public void refreshLocationCombo() {
        try {
            List<LocationDto> locationDtos = locationService.getAllLocations(LoginController.userDto.getId());
            ObservableList<LocationDto> observableList = FXCollections.observableArrayList(locationDtos);
            locationCombo.setItems(observableList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to refresh locations", ButtonType.OK).show();
        }
    }

    public void setReadOnly(boolean isReadOnly) {
        locationCombo.setDisable(isReadOnly);
        txtTitle.setDisable(isReadOnly);
        btnSave.setDisable(isReadOnly);
        addLocation.setDisable(isReadOnly);
        addMood.setDisable(isReadOnly);
        addTag.setDisable(isReadOnly);
    }
}