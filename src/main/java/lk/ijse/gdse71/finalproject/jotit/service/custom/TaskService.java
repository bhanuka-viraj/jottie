package lk.ijse.gdse71.finalproject.jotit.service.custom;


import lk.ijse.gdse71.finalproject.jotit.dto.TaskDto;
import lk.ijse.gdse71.finalproject.jotit.service.SuperService;

import java.util.List;

public interface TaskService extends SuperService {
    public boolean saveTask(TaskDto task) throws Exception;
    public boolean updateTask(TaskDto task) throws Exception;
    public TaskDto getTask(String desc) throws Exception;
    public List<TaskDto> getAllTask(String userId,String jotId) throws Exception;
    public boolean deleteTask(TaskDto task) throws Exception;
    public List<TaskDto> findTasks(String title) throws Exception;
}
