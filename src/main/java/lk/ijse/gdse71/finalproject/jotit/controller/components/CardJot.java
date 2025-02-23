package lk.ijse.gdse71.finalproject.jotit.controller.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gdse71.finalproject.jotit.controller.ControllerRef;
import lk.ijse.gdse71.finalproject.jotit.controller.ShareController;
import lk.ijse.gdse71.finalproject.jotit.controller.TaskManageController;
import lk.ijse.gdse71.finalproject.jotit.dto.*;
import lk.ijse.gdse71.finalproject.jotit.service.ServiceFactory;
import lk.ijse.gdse71.finalproject.jotit.service.ServiceType;
import lk.ijse.gdse71.finalproject.jotit.service.custom.JotService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.SharedJotService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.TaskService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.UserService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.JotServiceImpl;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.SharedJotServiceImpl;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.TaskServiceImpl;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.UserServiceImpl;
import org.ocpsoft.prettytime.PrettyTime;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class CardJot {

    @FXML
    private Label lblCategory;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblLocation;

    @FXML
    private Label lblTitle;

    @FXML
    private HBox moodHbox;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnShare;

    @FXML
    private HBox tagHbox;
    @FXML
    private AnchorPane pane;
    @FXML
    private PieChart pieChart;
    @FXML
    private Label lblTasks;
    @FXML
    private Label lblReceivedBy;

    private final JotService jotService =  (JotService) ServiceFactory.getInstance().getService(ServiceType.JOT);
    private final TaskService taskService = (TaskService) ServiceFactory.getInstance().getService(ServiceType.TASK);
    private final SharedJotService sharedJotService = (SharedJotService) ServiceFactory.getInstance().getService(ServiceType.SHAREDJOT);
    private final UserService userService = (UserService) ServiceFactory.getInstance().getService(ServiceType.USER);
    private JotDto jotDto;
    private boolean isReadOnly;
    private String userId;

    public void initialize() {
        lblReceivedBy.setVisible(false);
    }
    @FXML
    void cardClickOnAction(MouseEvent event) {

    }

    @FXML
    void openOnClicked(MouseEvent event) {
        if (jotDto != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addJot.fxml"));
                Parent root = loader.load();

                if (isReadOnly) {
                    ControllerRef.layoutController.setReadOnly(isReadOnly);
                }
                ControllerRef.layoutController.viewOnEdtitor(jotDto);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setData(JotDto jotDto){
        this.jotDto = jotDto;
        lblCategory.setText(jotDto.getCategory().getDescription());
        lblLocation.setText(jotDto.getLocation().getDescription());
        lblTitle.setText(jotDto.getTitle());

        Date date = jotDto.getUpdatedAt();
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        PrettyTime p = new PrettyTime();

        lblDate.setText("updated "+p.format(localDateTime));

        if (isReadOnly){
            showSendersName();
            btnShare.setVisible(false);
            btnDelete.setVisible(false);
        }

        for (MoodDto mood : jotDto.getMoods()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/card_mood.fxml"));
                Parent cardMoodRoot = loader.load();
                CardMood cardMoodController = loader.getController();
                cardMoodController.setData(mood);
                moodHbox.getChildren().add(cardMoodRoot);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (TagDto tag : jotDto.getTags()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/components/card_tag.fxml"));
                Parent cardTagRoot = loader.load();
                CardTag cardTagController = loader.getController();
                cardTagController.setData(tag);
                tagHbox.getChildren().add(cardTagRoot);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setPieChart();
    }

    private void showSendersName() {
        try {
            SharedJotDto sharedJotDto = sharedJotService.getSharedJotByJotIdAndReceiverId(
                    jotDto.getId(), userId
            );
            if (sharedJotDto != null) {
                UserDto sender = userService.getUserById(sharedJotDto.getUserBy());
                if (sender != null) {
                    lblReceivedBy.setText("Received by : "+sender.getUsername());
                    lblReceivedBy.setVisible(true);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setPieChart() {
        try {
            List<TaskDto> tasks = taskService.getAllTask(jotDto.getUserId(), jotDto.getId());

            int finishedTasks = 0;
            int runningTasks = 0;
            int notStartedTasks = 0;

            for (TaskDto task : tasks) {
                switch (task.getStatus()) {
                    case FINISHED -> finishedTasks++;
                    case RUNNING -> runningTasks++;
                    case NOT_STARTED -> notStartedTasks++;
                }
            }

            ObservableList<Data> pieChartData = FXCollections.observableArrayList(
                    new Data("Finished", finishedTasks),
                    new Data("Running", runningTasks),
                    new Data("Not Started", notStartedTasks)
            );
            pieChart.setData(pieChartData);
            pieChart.setLabelsVisible(false);

            lblTasks.setText("Tasks (" + tasks.size() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void btnTaskOnAction(ActionEvent event) {
        loadTasks();
    }

    private void loadTasks() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/task_manage.fxml"));
            Parent taskMangeView = loader.load();
            TaskManageController taskManageController = loader.getController();

            taskManageController.setJotDto(jotDto);
            taskManageController.setCardJotController(this);
            taskManageController.setReceiving(isReadOnly);


            Stage stage = new Stage();
            stage.setScene(new Scene(taskMangeView));
            stage.setTitle("Tasks");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading tasks" + e.getMessage(), ButtonType.OK).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        ButtonType result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this jot?",
                ButtonType.YES, ButtonType.NO).showAndWait().orElse(ButtonType.NO);

        if (result == ButtonType.YES) {
            try {
                String baseDir = System.getProperty("user.dir");

                Path jotPath = Paths.get(baseDir, jotDto.getPath());

                if (!Files.exists(jotPath)) {
                    System.err.println("Error: File does not exist at path: " + jotPath);
                    return;
                }

                boolean isDeleted = jotService.deleteJot(jotDto);
                if (isDeleted) {
                    Files.delete(jotPath);
                    new Alert(Alert.AlertType.INFORMATION, "Jot deleted").show();
                }
                ControllerRef.viewJotsController.loadJotCards();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error deleting jot", ButtonType.OK).show();
            }
        }
    }

    @FXML
    void btnShareOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/share.fxml"));
            Parent taskMangeView = loader.load();
            ShareController shareController = loader.getController();

            shareController.setJotDto(jotDto);

            Stage stage = new Stage();
            stage.setScene(new Scene(taskMangeView));
            stage.setTitle("Share jot");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setReadOnly(boolean isReceiving) {
        this.isReadOnly = isReceiving;
    }

    public void setLoggedUser(String id) {
        this.userId = id;
    }
}