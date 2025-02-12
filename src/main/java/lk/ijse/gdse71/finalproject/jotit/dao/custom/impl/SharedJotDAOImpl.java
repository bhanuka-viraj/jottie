package lk.ijse.gdse71.finalproject.jotit.dao.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dto.SharedJotStatus;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.SharedJotDAO;
import lk.ijse.gdse71.finalproject.jotit.entity.SharedJot;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SharedJotDAOImpl implements SharedJotDAO {
    @Override
    public boolean save(SharedJot sharedJot) throws Exception {
        return CrudUtil.execute("INSERT INTO shared_jot VALUES (?,?,?,?,?,?)",
                sharedJot.getId(),
                sharedJot.getJotId(),
                sharedJot.getUserBy(),
                sharedJot.getUserWith(),
                sharedJot.getStatus(),
                sharedJot.getDate());
    }

    @Override
    public List<SharedJot> getAllByReceiverId(String receiverId) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM shared_jot WHERE user_id_with = ?", receiverId);
        List<SharedJot> sharedJots = new ArrayList<>();
        while (resultSet.next()) {
            SharedJot sharedJot = new SharedJot();
            sharedJot.setId(resultSet.getString("shared_jot_id"));
            sharedJot.setJotId(resultSet.getString("jot_id"));
            sharedJot.setUserBy(resultSet.getString("user_id_by"));
            sharedJot.setUserWith(resultSet.getString("user_id_with"));
            String status = resultSet.getString("status");

            if (status!=null){
                SharedJotStatus sharedJotStatus = switch (status) {
                    case "not_seen" -> SharedJotStatus.NOT_SEEN;
                    case "seen" -> SharedJotStatus.SEEN;
                    case "removed" -> SharedJotStatus.REMOVED;
                    default -> null;
                };
                sharedJot.setStatus(sharedJotStatus);
            }

            //sharedJotDto.setDate(resultSet.getDate("date"));
            sharedJots.add(sharedJot);
        }
        return sharedJots;
    }

    @Override
    public SharedJot getSharedJotByJotIdAndReceiverId(String jotId, String receiverId) throws Exception{
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM shared_jot WHERE jot_id = ? AND user_id_with = ?", jotId, receiverId
        );

        if (resultSet.next()) {
            SharedJot sharedJot = new SharedJot();
            sharedJot.setId(resultSet.getString("shared_jot_id"));
            sharedJot.setJotId(resultSet.getString("jot_id"));
            sharedJot.setUserBy(resultSet.getString("user_id_by"));
            sharedJot.setUserWith(resultSet.getString("user_id_with"));
            String status = resultSet.getString("status");

            SharedJotStatus sharedJotStatus = switch (status != null ? status.toLowerCase() : "") {
                case "not_seen" -> SharedJotStatus.NOT_SEEN;
                case "seen" -> SharedJotStatus.SEEN;
                case "removed" -> SharedJotStatus.REMOVED;
                default -> null;
            };

            sharedJot.setStatus(sharedJotStatus);
            sharedJot.setDate(resultSet.getDate("date"));
            return sharedJot;
        }
        return null;
    }
}
