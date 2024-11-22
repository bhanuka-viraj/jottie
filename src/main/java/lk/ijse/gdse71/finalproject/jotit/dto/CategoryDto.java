package lk.ijse.gdse71.finalproject.jotit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private String id;
    private String description;
    private String userId;

    @Override
    public String toString() {
        return description;
    }
}
