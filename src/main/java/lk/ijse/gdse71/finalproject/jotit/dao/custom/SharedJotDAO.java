package lk.ijse.gdse71.finalproject.jotit.dao.custom;


import lk.ijse.gdse71.finalproject.jotit.dao.SuperDao;
import lk.ijse.gdse71.finalproject.jotit.entity.SharedJot;

import java.util.List;

public interface SharedJotDAO extends SuperDao {
    boolean save(SharedJot sharedJot)throws Exception;
    List<SharedJot> getAllByReceiverId(String userId)throws Exception;
    SharedJot getSharedJotByJotIdAndReceiverId(String id, String userId) throws Exception;
}
