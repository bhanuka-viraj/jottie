package lk.ijse.gdse71.finalproject.jotit.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.gdse71.finalproject.jotit.controller.components.CardJot;
import lk.ijse.gdse71.finalproject.jotit.dto.JotDto;
import lk.ijse.gdse71.finalproject.jotit.dto.TaskDto;
import lk.ijse.gdse71.finalproject.jotit.dto.TaskState;
import lk.ijse.gdse71.finalproject.jotit.service.ServiceFactory;
import lk.ijse.gdse71.finalproject.jotit.service.ServiceType;
import lk.ijse.gdse71.finalproject.jotit.service.custom.TaskService;
import lk.ijse.gdse71.finalproject.jotit.service.custom.impl.TaskServiceImpl;
import lk.ijse.gdse71.finalproject.jotit.util.IdGenerator;

import java.sql.Date;
import java.time.LocalDate;

public class AddTaskController {

    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private ComboBox<TaskState> statusCombo;

    @FXML
    private TextField txtDesc;
    private JotDto jotDto;
    private TaskDto taskDto;
    private final TaskService taskService = (TaskServiceImpl) ServiceFactory.getInstance().getService(ServiceType.TASK);
    private TaskManageController taskManageController;
    private CardJot cardJotController;

    @FXML
    public void initialize() {
        ObservableList<TaskState> taskStates = FXCollections.observableArrayList(TaskState.values());
        statusCombo.setItems(taskStates);
    }

    @FXML
    void btnSaveTaskOnAction(ActionEvent event) {
        try {
            String description = txtDesc.getText();
            LocalDate dueDate = dueDatePicker.getValue();
            TaskState status = statusCombo.getValue();

            if (description.isEmpty() || dueDate == null || status == null) {
                new Alert(Alert.AlertType.WARNING, "Please fill in all fields", ButtonType.OK).show();
                return;
            }

            TaskDto taskDto = new TaskDto();
            if (this.taskDto == null) {
                taskDto.setId(IdGenerator.generateId("TSK", 5));
            }else {
                taskDto.setId(this.taskDto.getId());
            }

            taskDto.setDesc(description);
            taskDto.setDueDate(Date.valueOf(dueDate));
            taskDto.setStatus(status);
            taskDto.setJotId(jotDto.getId());

            if (taskService.saveTask(taskDto)) {
                taskManageController.loadTaskData();
                txtDesc.getScene().getWindow().hide();
                cardJotController.setPieChart();
                new Alert(Alert.AlertType.INFORMATION, "Task saved successfully", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save task", ButtonType.OK).show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error saving task: " + e.getMessage(), ButtonType.OK).show();
        }
    }


    public void setJotDto(JotDto jotDto) {
        this.jotDto = jotDto;
    }

    public void setTaskManageController(TaskManageController taskManageController) {
        this.taskManageController = taskManageController;
    }

    public void setTaskDetails(TaskDto taskDto) {
        this.taskDto = taskDto;
        statusCombo.setValue(taskDto.getStatus());
        txtDesc.setText(taskDto.getDesc());
        taskDto.getDueDate();
        dueDatePicker.setValue(taskDto.getDueDate().toLocalDate());
    }

    public void setCardJotController(CardJot cardJotController) {
        this.cardJotController = cardJotController;
    }
}
