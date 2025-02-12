package lk.ijse.gdse71.finalproject.jotit.dao.custom.impl;
import lk.ijse.gdse71.finalproject.jotit.dto.TaskState;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.TaskDAO;
import lk.ijse.gdse71.finalproject.jotit.entity.Task;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {

    @Override
    public boolean saveTask(Task task) throws Exception {
        Task existingTask = getTask(task.getId());
        if (existingTask != null) {
            return updateTask(task);
        }
        return CrudUtil.execute(
                "INSERT INTO Task (task_id, user_id, jot_id, description, due_date, state) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                task.getId(),
                task.getUserId(),
                task.getJotId(),
                task.getDesc(),
                task.getDueDate(),
                task.getStatus().toString()
        );
    }

    @Override
    public boolean updateTask(Task task) throws Exception {
        return CrudUtil.execute(
                "UPDATE Task SET description = ?, due_date = ?, state = ? WHERE task_id = ?",
                task.getDesc(),
                task.getDueDate(),
                task.getStatus().toString(),
                task.getId()
        );
    }

    @Override
    public Task getTask(String id) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Task WHERE task_id = ?", id);
        if (resultSet.next()) {
            return new Task(
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
    public List<Task> getAllTask(String userId,String jotId) throws Exception {
                                                        // WHERE user_id = ?
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Task WHERE jot_id = ?", jotId);
        List<Task> tasks = new ArrayList<>();
        while (resultSet.next()) {
            tasks.add(new Task(
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
        return tasks;
    }

    @Override
    public boolean deleteTask(Task task) throws Exception {
        return CrudUtil.execute("DELETE FROM Task WHERE task_id = ?", task.getId());
    }

    @Override
    public List<Task> findTasks(String desc) throws Exception {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM Task WHERE description LIKE ? ORDER BY created_at DESC", "%" + desc + "%"
        );
        List<Task> tasks = new ArrayList<>();
        while (resultSet.next()) {
            tasks.add(new Task(
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
        return tasks;
    }
}