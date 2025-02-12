package lk.ijse.gdse71.finalproject.jotit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Relationship {
    private String id;
    private String type;
    private String createdBy;

    @Override
    public String toString() {
        return type ;
    }
}
