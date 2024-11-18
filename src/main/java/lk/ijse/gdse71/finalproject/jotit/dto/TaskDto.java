package lk.ijse.gdse71.finalproject.jotit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private String id;
    private String desc;
    private TaskState status;
    private String userId;
    private String jotId;
    private Date createdAt;
    private Date updatedAt;
    private java.sql.Date dueDate;
}
