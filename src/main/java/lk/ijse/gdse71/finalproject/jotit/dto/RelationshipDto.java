package lk.ijse.gdse71.finalproject.jotit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipDto {
    private String id;
    private String type;
    private String createdBy;

    @Override
    public String toString() {
        return type ;
    }
}
