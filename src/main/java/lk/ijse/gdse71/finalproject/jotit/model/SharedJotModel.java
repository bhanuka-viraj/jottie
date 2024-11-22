package lk.ijse.gdse71.finalproject.jotit.model;

import lk.ijse.gdse71.finalproject.jotit.dto.SharedJotDto;

import java.util.List;

public interface SharedJotModel {
    boolean save(SharedJotDto sharedJotDto)throws Exception;
    List<SharedJotDto> getAllByReceiverId(String userId)throws Exception;

    SharedJotDto getSharedJotByJotIdAndReceiverId(String id, String userId) throws Exception;
}
