package lk.ijse.gdse71.finalproject.jotit.service.custom;

import lk.ijse.gdse71.finalproject.jotit.dto.MoodDto;
import lk.ijse.gdse71.finalproject.jotit.service.SuperService;

import java.util.List;

public interface MoodService extends SuperService {
    public boolean saveMood(MoodDto mooddto) throws Exception;
    public MoodDto getMood(String desc) throws Exception;
    public boolean deleteMood(String desc) throws Exception;
    public List<MoodDto> getAllMoods() throws Exception;
}
