package lk.ijse.gdse71.finalproject.jotit.dto.tm;

import javafx.scene.control.Button;
import lk.ijse.gdse71.finalproject.jotit.dto.TaskState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskTm {
    private String desc;
    private TaskState status;
    private Date dueDate;
    private Button btnDelete;
    private Button btnUpdate;
}
