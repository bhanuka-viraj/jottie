package lk.ijse.gdse71.finalproject.jotit.model.impl;

import lk.ijse.gdse71.finalproject.jotit.dto.SharedJotDto;
import lk.ijse.gdse71.finalproject.jotit.dto.SharedJotStatus;
import lk.ijse.gdse71.finalproject.jotit.model.SharedJotModel;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SharedJotModelImpl implements SharedJotModel {
    @Override
    public boolean save(SharedJotDto sharedJotDto) throws Exception {
        return CrudUtil.execute("INSERT INTO shared_jot VALUES (?,?,?,?,?,?)",
                sharedJotDto.getId(),
                sharedJotDto.getJotId(),
                sharedJotDto.getUserBy(),
                sharedJotDto.getUserWith(),
                sharedJotDto.getStatus(),
                sharedJotDto.getDate());
    }

    @Override
    public List<SharedJotDto> getAllByReceiverId(String receiverId) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM shared_jot WHERE user_id_with = ?", receiverId);
        List<SharedJotDto> sharedJotDtos = new ArrayList<>();
        while (resultSet.next()) {
            SharedJotDto sharedJotDto = new SharedJotDto();
            sharedJotDto.setId(resultSet.getString("shared_jot_id"));
            sharedJotDto.setJotId(resultSet.getString("jot_id"));
            sharedJotDto.setUserBy(resultSet.getString("user_id_by"));
            sharedJotDto.setUserWith(resultSet.getString("user_id_with"));
            String status = resultSet.getString("status");

            if (status!=null){
                SharedJotStatus sharedJotStatus = switch (status) {
                    case "not_seen" -> SharedJotStatus.NOT_SEEN;
                    case "seen" -> SharedJotStatus.SEEN;
                    case "removed" -> SharedJotStatus.REMOVED;
                    default -> null;
                };
                sharedJotDto.setStatus(sharedJotStatus);
            }

            //sharedJotDto.setDate(resultSet.getDate("date"));
            sharedJotDtos.add(sharedJotDto);
        }
        return sharedJotDtos;
    }

    @Override
    public SharedJotDto getSharedJotByJotIdAndReceiverId(String jotId, String receiverId) throws Exception{
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM shared_jot WHERE jot_id = ? AND user_id_with = ?", jotId, receiverId
        );

        if (resultSet.next()) {
            SharedJotDto sharedJotDto = new SharedJotDto();
            sharedJotDto.setId(resultSet.getString("shared_jot_id"));
            sharedJotDto.setJotId(resultSet.getString("jot_id"));
            sharedJotDto.setUserBy(resultSet.getString("user_id_by"));
            sharedJotDto.setUserWith(resultSet.getString("user_id_with"));
            String status = resultSet.getString("status");

            SharedJotStatus sharedJotStatus = switch (status != null ? status.toLowerCase() : "") {
                case "not_seen" -> SharedJotStatus.NOT_SEEN;
                case "seen" -> SharedJotStatus.SEEN;
                case "removed" -> SharedJotStatus.REMOVED;
                default -> null;
            };

            sharedJotDto.setStatus(sharedJotStatus);
            sharedJotDto.setDate(resultSet.getDate("date"));
            return sharedJotDto;
        }
        return null;
    }
}
