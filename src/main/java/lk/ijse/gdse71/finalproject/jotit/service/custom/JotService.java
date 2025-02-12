package lk.ijse.gdse71.finalproject.jotit.service.custom;

import lk.ijse.gdse71.finalproject.jotit.dto.JotDto;
import lk.ijse.gdse71.finalproject.jotit.entity.Jot;
import lk.ijse.gdse71.finalproject.jotit.service.SuperService;

import java.util.List;

public interface JotService extends SuperService {
    public boolean saveJot(JotDto jotDto) throws Exception;
    public boolean updateJot(JotDto jotDto) throws Exception;
    public JotDto getJot(String str) throws Exception;
    public List<JotDto> getAllJot(String userId) throws Exception;
    public boolean deleteJot(JotDto jotDto) throws Exception;
    public List<JotDto> findJots(String title,String userID) throws Exception;
    public int getJotCountByCategory(String categoryId,String userID) throws Exception;
    List<JotDto> processJotDtos(List<Jot> jotDtos, String userId) throws Exception;
}
