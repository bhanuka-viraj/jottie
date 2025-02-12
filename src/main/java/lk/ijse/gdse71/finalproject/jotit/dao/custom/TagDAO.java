package lk.ijse.gdse71.finalproject.jotit.dao.custom;

import lk.ijse.gdse71.finalproject.jotit.dao.SuperDao;
import lk.ijse.gdse71.finalproject.jotit.entity.Tag;

import java.util.List;

public interface TagDAO extends SuperDao {
    public boolean saveTag(Tag tag) throws Exception;
    public Tag getTag(String desc,String userId) throws Exception;
    public List<Tag> getAllTags(String userId) throws Exception;
    public boolean deleteUnusedTags() throws Exception;

}
