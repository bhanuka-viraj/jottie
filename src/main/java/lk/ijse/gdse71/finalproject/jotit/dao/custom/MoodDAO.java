package lk.ijse.gdse71.finalproject.jotit.dao.custom;

import lk.ijse.gdse71.finalproject.jotit.dao.SuperDao;
import lk.ijse.gdse71.finalproject.jotit.entity.Mood;

import java.util.List;

public interface MoodDAO extends SuperDao {
    public boolean saveMood(Mood mood) throws Exception;
    public Mood getMood(String desc) throws Exception;
    public boolean deleteMood(String desc) throws Exception;
    public List<Mood> getAllMoods() throws Exception;
}
