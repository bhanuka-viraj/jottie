package lk.ijse.gdse71.finalproject.jotit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jot {
    private String id;
    private String userId;
    private String title;
    private String path;
    private String categoryId;
    private String locationId;
    private List<Mood> moods;
    private List<Tag> tags;
    private Date createdAt;
    private Date updatedAt;
}
