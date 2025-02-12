package lk.ijse.gdse71.finalproject.jotit.dao.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.entity.Mood;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.MoodDAO;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MoodDAOImpl implements MoodDAO {

    @Override
    public boolean saveMood(Mood mood) throws Exception {
        return CrudUtil.execute("INSERT INTO mood (mood_id, description) VALUES (?, ?)",
                mood.getId(), mood.getDescription());
    }

    @Override
    public Mood getMood(String desc) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM mood WHERE description = ?", desc);
        if (resultSet.next()) {
            return new Mood(resultSet.getString("mood_id"), resultSet.getString("description"));
        }
        return null;
    }

    @Override
    public boolean deleteMood(String desc) throws Exception {
        return CrudUtil.execute("DELETE FROM mood WHERE description = ?", Integer.parseInt(desc));
    }

    @Override
    public List<Mood> getAllMoods() throws Exception {
        List<Mood> moodDtos = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM mood");
        while (resultSet.next()) {
            moodDtos.add(new Mood(resultSet.getString("mood_id"), resultSet.getString("description")));
        }
        return moodDtos;
    }
}