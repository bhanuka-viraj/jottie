package lk.ijse.gdse71.finalproject.jotit.dao.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dao.custom.TagDAO;
import lk.ijse.gdse71.finalproject.jotit.entity.Tag;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TagDAOImpl implements TagDAO {
    @Override
    public boolean saveTag(Tag tag) throws Exception {
        return CrudUtil.execute("INSERT INTO tag VALUES (?, ?,?)",
                tag.getId(),
                tag.getName(),
                tag.getCreatedBy());
    }

    @Override
    public Tag getTag(String name,String userId) throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM tag WHERE name = ? AND created_by = ?", name, userId);
        if (rs.next()) {
            return new Tag(rs.getString("tag_id"), rs.getString("name"), rs.getString("created_by"));
        }
        return null;
    }

    @Override
    public List<Tag> getAllTags(String userId) throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM tag WHERE created_by = ?", userId);
        List<Tag> tags = new ArrayList<>();
        while (rs.next()) {
            tags.add(new Tag(rs.getString("tag_id"), rs.getString("name"), rs.getString("created_by")));
        }
        return tags;
    }

    @Override
    public boolean deleteUnusedTags() throws Exception {
        return CrudUtil.execute("DELETE FROM Tag WHERE tag_id NOT IN (SELECT DISTINCT tag_id FROM jot_tag)");
    }
}
