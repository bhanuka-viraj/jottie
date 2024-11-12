package lk.ijse.gdse71.finalproject.jotit.model;

import lk.ijse.gdse71.finalproject.jotit.dto.MoodDto;

import java.util.List;

public interface MoodModel {
    public void saveMood(MoodDto moodDto) throws Exception;
    public MoodDto getMood(String id) throws Exception;
    public void deleteMood(String id) throws Exception;
    public List<MoodDto> getAllMoods() throws Exception;
}
