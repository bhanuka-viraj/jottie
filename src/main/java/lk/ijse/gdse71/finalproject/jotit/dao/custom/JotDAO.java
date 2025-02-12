package lk.ijse.gdse71.finalproject.jotit.dao.custom;

import lk.ijse.gdse71.finalproject.jotit.dao.SuperDao;
import lk.ijse.gdse71.finalproject.jotit.entity.Jot;

import java.util.List;

public interface JotDAO extends SuperDao {
    public boolean saveJot(Jot jot) throws Exception;
public boolean updateJot(Jot jot) throws Exception;
    public Jot getJot(String id) throws Exception;
    public List<Jot> getAllJot(String userId) throws Exception;
    public boolean deleteJot(Jot jot) throws Exception;
    public List<Jot> findJots(String title,String userID) throws Exception;
    public int getJotCountByCategory(String categoryId,String userID) throws Exception;
}
