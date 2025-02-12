package lk.ijse.gdse71.finalproject.jotit.dao.custom;

import lk.ijse.gdse71.finalproject.jotit.dao.SuperDao;
import lk.ijse.gdse71.finalproject.jotit.entity.Task;

import java.util.List;

public interface TaskDAO extends SuperDao {
    public boolean saveTask(Task task) throws Exception;
    public boolean updateTask(Task task) throws Exception;
    public Task getTask(String desc) throws Exception;
    public List<Task> getAllTask(String userId,String jotId) throws Exception;
    public boolean deleteTask(Task task) throws Exception;
    public List<Task> findTasks(String title) throws Exception;
}
