package lk.ijse.gdse71.finalproject.jotit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JotDto {
    private String id;
    private String userId;
    private String title;
    private String path;
    private CategoryDto category;
    private LocationDto location;
    private List<MoodDto> moods;
    private List<TagDto> tags;
    private Date createdAt;
    private Date updatedAt;
}
