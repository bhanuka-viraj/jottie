package lk.ijse.gdse71.finalproject.jotit.model.impl;

import lk.ijse.gdse71.finalproject.jotit.dto.TagDto;
import lk.ijse.gdse71.finalproject.jotit.model.TagModel;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TagModelImpl implements TagModel {
    @Override
    public boolean saveTag(TagDto tagDto) throws Exception {
        return CrudUtil.execute("INSERT INTO tag VALUES (?, ?)",
                tagDto.getId(),
                tagDto.getName());
    }

    @Override
    public TagDto getTag(String name) throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM tag WHERE name = ? ", name);
        if (rs.next()) {
            return new TagDto(rs.getString("tag_id"), rs.getString("name"));
        }
        return null;
    }

    @Override
    public List<TagDto> getAllTags() throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM tag");
        List<TagDto> tags = new ArrayList<>();
        while (rs.next()) {
            tags.add(new TagDto(rs.getString("tag_id"), rs.getString("name")));
        }
        return tags;
    }

    @Override
    public boolean deleteUnusedTags() throws Exception {
        return CrudUtil.execute("DELETE FROM Tag WHERE tag_id NOT IN (SELECT DISTINCT tag_id FROM jot_tag)");
    }
}
