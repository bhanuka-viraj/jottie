package lk.ijse.gdse71.finalproject.jotit.service.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dao.custom.TagDAO;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.impl.TagDAOImpl;
import lk.ijse.gdse71.finalproject.jotit.dto.TagDto;
import lk.ijse.gdse71.finalproject.jotit.entity.Tag;
import lk.ijse.gdse71.finalproject.jotit.service.custom.TagService;

import java.util.List;

public class TagServiceImpl implements TagService {
    private final TagDAO tagDAO = new TagDAOImpl();
    @Override
    public boolean saveTag(TagDto tag) throws Exception {
        return tagDAO.saveTag(new Tag(tag.getId(),tag.getName(),tag.getCreatedBy()));
    }

    @Override
    public TagDto getTag(String desc, String userId) throws Exception {
        Tag tag = tagDAO.getTag(desc,userId);
        return new TagDto(tag.getId(),tag.getName(),tag.getCreatedBy());
    }

    @Override
    public List<TagDto> getAllTags(String userId) throws Exception {
        return tagDAO.getAllTags(userId).stream().map(t -> new TagDto(t.getId(),t.getName(),t.getCreatedBy())).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public boolean deleteUnusedTags() throws Exception {
        return tagDAO.deleteUnusedTags();
    }
}
