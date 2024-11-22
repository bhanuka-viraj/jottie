package lk.ijse.gdse71.finalproject.jotit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharedJotDto {
    private String id;
    private String jotId;
    private String userBy;
    private String userWith;
    private SharedJotStatus status;
    private Date date;
}
