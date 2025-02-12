package lk.ijse.gdse71.finalproject.jotit.dao.custom;

import lk.ijse.gdse71.finalproject.jotit.dao.SuperDao;
import lk.ijse.gdse71.finalproject.jotit.dto.MoodDto;
import lk.ijse.gdse71.finalproject.jotit.dto.TagDto;

import java.sql.SQLException;
import java.util.List;

public interface QueryDAO extends SuperDao {
    public List<TagDto> getTagsByJotId(String jotId, String userId) throws SQLException, ClassNotFoundException;

    public List<MoodDto> getMoodsByJotId(String jotId) throws SQLException, ClassNotFoundException;


    boolean saveJotTag(String jotId, String tagId) throws SQLException, ClassNotFoundException;

    boolean saveJotMood(String jotId, String moodId) throws SQLException, ClassNotFoundException;
    boolean deleteJotTag(String jotId) throws SQLException, ClassNotFoundException;
    boolean deleteJotMood(String jotId) throws SQLException, ClassNotFoundException;

}
