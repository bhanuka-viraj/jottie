package lk.ijse.gdse71.finalproject.jotit.service.custom;


import lk.ijse.gdse71.finalproject.jotit.dto.TagDto;
import lk.ijse.gdse71.finalproject.jotit.service.SuperService;

import java.util.List;

public interface TagService extends SuperService {
    public boolean saveTag(TagDto tag) throws Exception;
    public TagDto getTag(String desc,String userId) throws Exception;
    public List<TagDto> getAllTags(String userId) throws Exception;
    public boolean deleteUnusedTags() throws Exception;

}
