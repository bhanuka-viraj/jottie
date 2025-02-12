package lk.ijse.gdse71.finalproject.jotit.service.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dao.custom.MoodDAO;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.impl.MoodDAOImpl;
import lk.ijse.gdse71.finalproject.jotit.dto.MoodDto;
import lk.ijse.gdse71.finalproject.jotit.entity.Mood;
import lk.ijse.gdse71.finalproject.jotit.service.custom.MoodService;

import java.util.List;
import java.util.stream.Collectors;

public class MoodServiceImpl implements MoodService {
    private final MoodDAO moodDAO = new MoodDAOImpl();
    @Override
    public boolean saveMood(MoodDto mooddto) throws Exception {
        return moodDAO.saveMood(new Mood(mooddto.getId(),mooddto.getDescription()));
    }

    @Override
    public MoodDto getMood(String desc) throws Exception {
        Mood mood = moodDAO.getMood(desc);
        return new MoodDto(mood.getId(),mood.getDescription());
    }

    @Override
    public boolean deleteMood(String desc) throws Exception {
        return moodDAO.deleteMood(desc);
    }

    @Override
    public List<MoodDto> getAllMoods() throws Exception {
        return moodDAO.getAllMoods().stream()
                .map(mood -> new MoodDto(mood.getId(), mood.getDescription()))
                .collect(Collectors.toList());
    }
}
