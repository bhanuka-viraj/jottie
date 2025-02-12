package lk.ijse.gdse71.finalproject.jotit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private String id;
    private String description;
    private String userId;

    @Override
    public String toString() {
        return description;
    }
}
