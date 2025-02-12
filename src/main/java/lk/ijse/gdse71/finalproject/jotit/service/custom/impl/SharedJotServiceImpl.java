package lk.ijse.gdse71.finalproject.jotit.service.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dao.custom.SharedJotDAO;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.impl.SharedJotDAOImpl;
import lk.ijse.gdse71.finalproject.jotit.dto.SharedJotDto;
import lk.ijse.gdse71.finalproject.jotit.entity.SharedJot;
import lk.ijse.gdse71.finalproject.jotit.service.custom.SharedJotService;

import java.util.List;

public class SharedJotServiceImpl implements SharedJotService {
    private final SharedJotDAO sharedJotDAO = new SharedJotDAOImpl();
    @Override
    public boolean save(SharedJotDto sharedJot) throws Exception {
        return sharedJotDAO.save(new SharedJot(sharedJot.getId(), sharedJot.getJotId(), sharedJot.getUserBy(), sharedJot.getUserWith(), sharedJot.getStatus(), sharedJot.getDate()));
    }

    @Override
    public List<SharedJotDto> getAllByReceiverId(String userId) throws Exception {
        return sharedJotDAO.getAllByReceiverId(userId).stream().map(s -> new SharedJotDto(s.getId(), s.getJotId(), s.getUserBy(), s.getUserWith(), s.getStatus(), s.getDate())).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public SharedJotDto getSharedJotByJotIdAndReceiverId(String id, String userId) throws Exception {
        SharedJot sharedJot = sharedJotDAO.getSharedJotByJotIdAndReceiverId(id, userId);
        return new SharedJotDto(sharedJot.getId(), sharedJot.getJotId(), sharedJot.getUserBy(), sharedJot.getUserWith(), sharedJot.getStatus(), sharedJot.getDate());
    }
}
