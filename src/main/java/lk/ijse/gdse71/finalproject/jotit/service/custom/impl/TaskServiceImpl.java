package lk.ijse.gdse71.finalproject.jotit.service.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dao.DaoFactory;
import lk.ijse.gdse71.finalproject.jotit.dao.DaoType;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.TaskDAO;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.impl.TaskDAOImpl;
import lk.ijse.gdse71.finalproject.jotit.dto.TaskDto;
import lk.ijse.gdse71.finalproject.jotit.entity.Task;
import lk.ijse.gdse71.finalproject.jotit.service.custom.TaskService;

import java.util.List;

public class TaskServiceImpl implements TaskService {
    private TaskDAO taskDAO = (TaskDAOImpl) DaoFactory.getInstance().getDao(DaoType.TASK);
    @Override
    public boolean saveTask(TaskDto task) throws Exception {
        return taskDAO.saveTask(new Task(task.getId(), task.getDesc(), task.getStatus(),task.getUserId(),task.getJotId(),task.getCreatedAt(),task.getUpdatedAt(),task.getDueDate()));
    }

    @Override
    public boolean updateTask(TaskDto task) throws Exception {
        return taskDAO.updateTask(new Task(task.getId(), task.getDesc(), task.getStatus(),task.getUserId(),task.getJotId(),task.getCreatedAt(),task.getUpdatedAt(),task.getDueDate()));
    }

    @Override
    public TaskDto getTask(String desc) throws Exception {
        Task task = taskDAO.getTask(desc);
        return new TaskDto(task.getId(), task.getDesc(), task.getStatus(),task.getUserId(),task.getJotId(),task.getCreatedAt(),task.getUpdatedAt(),task.getDueDate());
    }

    @Override
    public List<TaskDto> getAllTask(String userId, String jotId) throws Exception {
        return taskDAO.getAllTask(userId,jotId).stream().map(task -> new TaskDto(task.getId(), task.getDesc(), task.getStatus(),task.getUserId(),task.getJotId(),task.getCreatedAt(),task.getUpdatedAt(),task.getDueDate())).toList();
    }

    @Override
    public boolean deleteTask(TaskDto task) throws Exception {
        return taskDAO.deleteTask(new Task(task.getId(), task.getDesc(), task.getStatus(),task.getUserId(),task.getJotId(),task.getCreatedAt(),task.getUpdatedAt(),task.getDueDate()));
    }

    @Override
    public List<TaskDto> findTasks(String title) throws Exception {
        return taskDAO.findTasks(title).stream().map(task -> new TaskDto(task.getId(), task.getDesc(), task.getStatus(),task.getUserId(),task.getJotId(),task.getCreatedAt(),task.getUpdatedAt(),task.getDueDate())).toList();
    }
}
