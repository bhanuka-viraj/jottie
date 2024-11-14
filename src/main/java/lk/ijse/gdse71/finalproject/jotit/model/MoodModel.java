package lk.ijse.gdse71.finalproject.jotit.model;

import lk.ijse.gdse71.finalproject.jotit.dto.MoodDto;

import java.util.List;

public interface MoodModel {
    public boolean saveMood(MoodDto moodDto) throws Exception;
    public MoodDto getMood(String desc) throws Exception;
    public boolean deleteMood(String desc) throws Exception;
    public List<MoodDto> getAllMoods() throws Exception;
}
