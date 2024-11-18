package lk.ijse.gdse71.finalproject.jotit.model;

import lk.ijse.gdse71.finalproject.jotit.dto.TaskDto;

import java.util.List;

public interface TaskModel {
    public boolean saveTask(TaskDto taskDto) throws Exception;
    public boolean updateTask(TaskDto taskDto) throws Exception;
    public TaskDto getTask(String desc) throws Exception;
    public List<TaskDto> getAllTask(String userId,String jotId) throws Exception;
    public boolean deleteTask(TaskDto taskDto) throws Exception;
    public List<TaskDto> findTasks(String title) throws Exception;
}
