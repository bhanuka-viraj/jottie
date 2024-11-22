package lk.ijse.gdse71.finalproject.jotit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRelationshipDto {
    private String id;
    private String userId;
    private String relationshipId;
    private String addedById;
}
