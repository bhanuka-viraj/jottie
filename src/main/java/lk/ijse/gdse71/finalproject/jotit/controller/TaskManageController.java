package lk.ijse.gdse71.finalproject.jotit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gdse71.finalproject.jotit.controller.components.CardJot;
import lk.ijse.gdse71.finalproject.jotit.dto.JotDto;
import lk.ijse.gdse71.finalproject.jotit.dto.TaskDto;
import lk.ijse.gdse71.finalproject.jotit.dto.tm.TaskTm;
import lk.ijse.gdse71.finalproject.jotit.service.ServiceFactory;
import lk.ijse.gdse71.finalproject.jotit.service.ServiceType;
import lk.ijse.gdse71.finalproject.jotit.service.custom.TaskService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.TaskServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskManageController {
    @FXML
    private TableColumn<TaskTm, String> description;

    @FXML
    private TableColumn<TaskTm, Date> dueDate;


    @FXML
    private TableColumn<TaskTm, String> status;

    @FXML
    private TableView<TaskTm> tblTasks;

    @FXML
    private TableColumn<TaskTm, Button> update;
    @FXML
    private TableColumn<TaskTm, Button> delete;

    @FXML
    private Label lblDescription;

    private final TaskService taskService = (TaskServiceImpl) ServiceFactory.getInstance().getService(ServiceType.TASK);
    private AddTaskController addTaskController;
    private JotDto jotDto;
    private CardJot cardJotController;
    private boolean isReceiving;

    @FXML
    public void initialize() {
        // Initialize table columns
        description.setCellValueFactory(new PropertyValueFactory<>("desc"));
        dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        delete.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
        update.setCellValueFactory(new PropertyValueFactory<>("btnUpdate"));

        tblTasks.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                lblDescription.setText(newSelection.getDesc());
            } else {
                lblDescription.setText(""); // Clear the label if no row is selected
            }
        });

    }
    public void setJotDto(JotDto jotDto) {
        this.jotDto = jotDto;
        loadTaskData();
    }
    @FXML
    public void btnAddTaskOnAction(ActionEvent actionEvent) {
        if (isReceiving){
            new Alert(Alert.AlertType.WARNING, "you cannot new tasks to received jots", ButtonType.OK).show();
            return;
        }
        loadAddTask(null);
    }

    private void loadAddTask(TaskDto taskDto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addTask.fxml"));
            Parent addTaskView = loader.load();
            addTaskController = loader.getController();

            addTaskController.setJotDto(jotDto);
            addTaskController.setTaskManageController(this);
            addTaskController.setCardJotController(this.cardJotController);

            if (taskDto != null) {
                System.out.println(taskDto.getDueDate());
                addTaskController.setTaskDetails(taskDto);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(addTaskView));
            stage.setTitle("Tasks");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading tasks" + e.getMessage(), ButtonType.OK).show();
            e.printStackTrace();
        }
    }

    public void loadTaskData() {
        try {
            if (jotDto != null) {
                List<TaskDto> tasks = taskService.getAllTask(jotDto.getUserId(),jotDto.getId());

                List<TaskTm> taskTms = new ArrayList<>();
                for (TaskDto taskDto : tasks) {
                    TaskTm taskTm = createTaskTm(taskDto);
                    taskTms.add(taskTm);
                }

                ObservableList<TaskTm> observableTasks = FXCollections.observableArrayList(taskTms);
                tblTasks.setItems(observableTasks);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading tasks: " + e.getMessage(), ButtonType.OK).show();
        }
    }

    private TaskTm createTaskTm(TaskDto taskDto) {
        Button btnDelete = new Button("Delete");
        btnDelete.setOnAction(event -> deleteTask(taskDto));
        btnDelete.getStyleClass().add("btnDelete");

        Button btnUpdate = new Button("Update");
        btnUpdate.setOnAction(event -> updateTask(taskDto));
        btnUpdate.getStyleClass().add("btnUpdate");

        return new TaskTm(
                taskDto.getDesc(),
                taskDto.getStatus(),
                taskDto.getDueDate(),
                btnDelete,
                btnUpdate
        );
    }

    private void deleteTask(TaskDto taskDto) {
        if (isReceiving){
            new Alert(Alert.AlertType.WARNING, "you cannot delete tasks from received jots", ButtonType.OK).show();
            return;
        }
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this task?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    taskService.deleteTask(taskDto);
                    loadTaskData();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete task", ButtonType.OK).show();
                }
            }
        });
    }
    private void updateTask(TaskDto taskDto) {
        if (isReceiving){
            new Alert(Alert.AlertType.WARNING, "you cannot update tasks from received jots", ButtonType.OK).show();
            return;
        }
        loadAddTask(taskDto);
    }

    public void setCardJotController(CardJot cardJot) {
        this.cardJotController = cardJot;
    }

    public void setReceiving(boolean isReceiving) {
        this.isReceiving = isReceiving;
    }
}
