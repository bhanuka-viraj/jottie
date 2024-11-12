package lk.ijse.gdse71.finalproject.jotit.model;

import lk.ijse.gdse71.finalproject.jotit.dto.TagDto;

import java.util.List;

public interface TagModel {
    public boolean saveTag(TagDto tagDto) throws Exception;
    public TagDto getTag(String id) throws Exception;
    public List<TagDto> getAllTags() throws Exception;
    public boolean deleteTag(TagDto tagDto) throws Exception;

}
