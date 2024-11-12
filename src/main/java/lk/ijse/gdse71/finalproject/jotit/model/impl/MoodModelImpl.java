package lk.ijse.gdse71.finalproject.jotit.model.impl;

import lk.ijse.gdse71.finalproject.jotit.dto.MoodDto;
import lk.ijse.gdse71.finalproject.jotit.model.MoodModel;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoodModelImpl implements MoodModel {

    @Override
    public boolean saveMood(MoodDto moodDto) throws Exception {
        return CrudUtil.execute("INSERT INTO mood (mood_id, description) VALUES (?, ?)",
                moodDto.getId(), moodDto.getDescription());
    }

    @Override
    public MoodDto getMood(String id) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM mood WHERE mood_id = ?", Integer.parseInt(id));
        if (resultSet.next()) {
            return new MoodDto(resultSet.getString("mood_id"), resultSet.getString("description"));
        }
        return null;
    }

    @Override
    public boolean deleteMood(String id) throws Exception {
        return CrudUtil.execute("DELETE FROM mood WHERE mood_id = ?", Integer.parseInt(id));
    }

    @Override
    public List<MoodDto> getAllMoods() throws Exception {
        List<MoodDto> moodDtos = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM mood");
        while (resultSet.next()) {
            moodDtos.add(new MoodDto(resultSet.getString("mood_id"), resultSet.getString("description")));
        }
        return moodDtos;
    }
}