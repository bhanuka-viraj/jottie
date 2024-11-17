package lk.ijse.gdse71.finalproject.jotit.model;

import lk.ijse.gdse71.finalproject.jotit.dto.JotDto;

import java.util.List;

public interface JotModel {
    public boolean saveJot(JotDto jotDto) throws Exception;
    public JotDto getJot(String str) throws Exception;
    public List<JotDto> getAllJot(String userId) throws Exception;
    public boolean deleteJot(JotDto jotDto) throws Exception;
    public List<JotDto> findJots(String title) throws Exception;
}
