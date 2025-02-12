package lk.ijse.gdse71.finalproject.jotit.dao.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dto.*;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.QueryDAO;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryDaoImpl implements QueryDAO {

    @Override
    public List<TagDto> getTagsByJotId(String jotId, String userId) throws SQLException, ClassNotFoundException {
        ResultSet tagResultSet ;

        if (userId != null) {
           tagResultSet = CrudUtil.execute(
                    "SELECT * FROM tag t " +
                            "JOIN jot_tag jt ON t.tag_id = jt.tag_id " +
                            "WHERE jt.jot_id = ? AND t.created_by = ?", jotId, userId);
        }else {
            tagResultSet = CrudUtil.execute(
                    "SELECT * FROM tag t " +
                            "JOIN jot_tag jt ON t.tag_id = jt.tag_id " +
                            "WHERE jt.jot_id = ? ", jotId);
        }
        List<TagDto> tags = new ArrayList<>();
        while (tagResultSet.next()) {
            tags.add(new TagDto(tagResultSet.getString("tag_id"), tagResultSet.getString("name"), tagResultSet.getString("created_by")));
        }
        return tags;
    }

    @Override
    public List<MoodDto> getMoodsByJotId(String jotId) throws SQLException, ClassNotFoundException {
        ResultSet jotMoods = CrudUtil.execute(
                "SELECT * FROM mood m " +
                        "JOIN jot_mood jm ON m.mood_id = jm.mood_id " +
                        "WHERE jm.jot_id = ?", jotId);
        List<MoodDto> moods = new ArrayList<>();
        while (jotMoods.next()) {
            moods.add(new MoodDto(jotMoods.getString("mood_id"), jotMoods.getString("description")));
        }
        return moods;
    }


    @Override
    public boolean saveJotTag(String jotId, String tagId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO jot_tag VALUES (?,?)", jotId, tagId);
    }

    @Override
    public boolean saveJotMood(String jotId, String moodId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO jot_mood VALUES (?,?)", jotId, moodId);
    }

    @Override
    public boolean deleteJotTag(String jotId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM jot_tag WHERE jot_id = ?", jotId);
    }

    @Override
    public boolean deleteJotMood(String jotId) throws SQLException, ClassNotFoundException {
        return  CrudUtil.execute("DELETE FROM jot_mood WHERE jot_id = ?", jotId);
    }


}
