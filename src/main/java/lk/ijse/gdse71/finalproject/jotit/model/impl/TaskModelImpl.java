package lk.ijse.gdse71.finalproject.jotit.model.impl;
import lk.ijse.gdse71.finalproject.jotit.dto.TaskDto;
import lk.ijse.gdse71.finalproject.jotit.dto.TaskState;
import lk.ijse.gdse71.finalproject.jotit.model.TaskModel;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskModelImpl implements TaskModel {

    @Override
    public boolean saveTask(TaskDto taskDto) throws Exception {
        TaskDto task = getTask(taskDto.getId());
        if (task != null) {
            return updateTask(taskDto);
        }
        return CrudUtil.execute(
                "INSERT INTO Task (task_id, user_id, jot_id, description, due_date, state) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                taskDto.getId(),
                taskDto.getUserId(),
                taskDto.getJotId(),
                taskDto.getDesc(),
                taskDto.getDueDate(),
                taskDto.getStatus().toString()
        );
    }

    @Override
    public boolean updateTask(TaskDto taskDto) throws Exception {
        return CrudUtil.execute(
                "UPDATE Task SET description = ?, due_date = ?, state = ? WHERE task_id = ?",
                taskDto.getDesc(),
                taskDto.getDueDate(),
                taskDto.getStatus().toString(),
                taskDto.getId()
        );
    }

    @Override
    public TaskDto getTask(String id) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Task WHERE task_id = ?", id);
        if (resultSet.next()) {
            return new TaskDto(
                    resultSet.getString("task_id"),
                    resultSet.getString("description"),
                    TaskState.valueOf(resultSet.getString("state")), // Convert string to TaskState
                    resultSet.getString("user_id"),
                    resultSet.getString("jot_id"),
                    resultSet.getTimestamp("created_at"),
                    resultSet.getTimestamp("updated_at"),
                    resultSet.getDate("due_date")
            );
        }
        return null;
    }

    @Override
    public List<TaskDto> getAllTask(String userId,String jotId) throws Exception {
                                                        // WHERE user_id = ?
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Task WHERE jot_id = ?", jotId);
        List<TaskDto> taskDtos = new ArrayList<>();
        while (resultSet.next()) {
            taskDtos.add(new TaskDto(
                    resultSet.getString("task_id"),
                    resultSet.getString("description"),
                    TaskState.valueOf(resultSet.getString("state")),
                    resultSet.getString("user_id"),
                    resultSet.getString("jot_id"),
                    resultSet.getTimestamp("created_at"),
                    resultSet.getTimestamp("updated_at"),
                    resultSet.getDate("due_date")
            ));
        }
        return taskDtos;
    }

    @Override
    public boolean deleteTask(TaskDto taskDto) throws Exception {
        return CrudUtil.execute("DELETE FROM Task WHERE task_id = ?", taskDto.getId());
    }

    @Override
    public List<TaskDto> findTasks(String desc) throws Exception {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM Task WHERE description LIKE ? ORDER BY created_at DESC", "%" + desc + "%"
        );
        List<TaskDto> taskDtos = new ArrayList<>();
        while (resultSet.next()) {
            taskDtos.add(new TaskDto(
                    resultSet.getString("task_id"),
                    resultSet.getString("description"),
                    TaskState.valueOf(resultSet.getString("state")),
                    resultSet.getString("user_id"),
                    resultSet.getString("jot_id"),
                    resultSet.getTimestamp("created_at"),
                    resultSet.getTimestamp("updated_at"),
                    resultSet.getDate("due_date")
            ));
        }
        return taskDtos;
    }
}