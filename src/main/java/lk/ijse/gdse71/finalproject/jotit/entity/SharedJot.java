package lk.ijse.gdse71.finalproject.jotit.entity;

import lk.ijse.gdse71.finalproject.jotit.dto.SharedJotStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedJot {
    private String id;
    private String jotId;
    private String userBy;
    private String userWith;
    private SharedJotStatus status;
    private Date date;
}
