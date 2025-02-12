package lk.ijse.gdse71.finalproject.jotit.service.custom;



import lk.ijse.gdse71.finalproject.jotit.dto.SharedJotDto;
import lk.ijse.gdse71.finalproject.jotit.service.SuperService;

import java.util.List;

public interface SharedJotService extends SuperService {
    boolean save(SharedJotDto sharedJot)throws Exception;
    List<SharedJotDto> getAllByReceiverId(String userId)throws Exception;
    SharedJotDto getSharedJotByJotIdAndReceiverId(String id, String userId) throws Exception;
}
