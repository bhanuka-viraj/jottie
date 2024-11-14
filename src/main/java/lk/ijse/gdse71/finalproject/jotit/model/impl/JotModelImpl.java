package lk.ijse.gdse71.finalproject.jotit.model.impl;

import lk.ijse.gdse71.finalproject.jotit.dto.*;
import lk.ijse.gdse71.finalproject.jotit.model.CategoryModel;
import lk.ijse.gdse71.finalproject.jotit.model.JotModel;
import lk.ijse.gdse71.finalproject.jotit.model.LocationModel;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JotModelImpl implements JotModel {
    private final CategoryModel categoryModel = new CategoryModelImpl();
    private final LocationModel locationModel = new LocationModelImpl();

    @Override
    public boolean saveJot(JotDto jotDto) throws Exception {
        try {
            CrudUtil.beginTransaction();

            boolean isJotSaved = CrudUtil.execute(
                    "INSERT INTO jot (jot_id, user_id, title, path, category_id, location_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    jotDto.getId(),
                    jotDto.getUserId(),
                    jotDto.getTitle(),
                    jotDto.getPath(),
                    jotDto.getCategory().getId(),
                    jotDto.getLocation().getId()
            );

            if (isJotSaved) {
                boolean isJotTagSaved = false;
                List<TagDto> tags = jotDto.getTags();
                for (TagDto tag : tags) {
                    isJotTagSaved = CrudUtil.execute("INSERT INTO jot_tag VALUES (?,?)", jotDto.getId(), tag.getId());
                }

                if (isJotTagSaved) {
                    boolean isMoodSaved = false;
                    List<MoodDto> moods = jotDto.getMoods();
                    System.out.println(moods);
                    for (MoodDto mood : moods) {
                        isMoodSaved = CrudUtil.execute("INSERT INTO jot_mood VALUES (?,?)", jotDto.getId(), mood.getId());
                    }

                    if (isMoodSaved) {
                        CrudUtil.commitTransaction();
                    }
                } else {
                    CrudUtil.rollbackTransaction();
                    return false;
                }

            } else {
                CrudUtil.rollbackTransaction();
                return false;
            }
            return true;

        } catch (SQLException e) {
            CrudUtil.rollbackTransaction();
            throw e;
        } finally {
            CrudUtil.closeConnection();
        }
    }

    @Override
    public JotDto getJot(String id) throws Exception {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT j.jot_id, j.user_id, j.title, j.path, j.created_at, j.updated_at, " +
                        "c.category_id, c.description AS category_description, " +
                        "l.location_id, l.description AS location_description " +
                        "FROM jot j " +
                        "JOIN category c ON j.category_id = c.category_id " +
                        "JOIN location l ON j.location_id = l.location_id " +
                        "WHERE j.jot_id = ?", id);

        if (resultSet.next()) {
            JotDto jotDto = new JotDto();
            jotDto.setId(resultSet.getString("jot_id"));
            jotDto.setUserId(resultSet.getString("user_id"));
            jotDto.setTitle(resultSet.getString("title"));
            jotDto.setPath(resultSet.getString("path"));

            jotDto.setCategory(new CategoryDto(resultSet.getString("category_id"),
                    resultSet.getString("category_description")));

            jotDto.setLocation(new LocationDto(resultSet.getString("location_id"),
                    resultSet.getString("location_description")));

            jotDto.setCreatedAt(resultSet.getTimestamp("created_at"));
            jotDto.setUpdatedAt(resultSet.getTimestamp("updated_at"));

            List<TagDto> tags = new ArrayList<>();
            ResultSet tagResultSet  = CrudUtil.execute(
                    "SELECT * FROM tag t" +
                    "JOIN jot_tag jt ON t.tag_id = jt.tag_id " +
                    "WHERE jt.jot_id = ?", id);
            while (tagResultSet .next()) {
                tags.add(new TagDto(tagResultSet .getString("tag_id"), tagResultSet .getString("name")));
            }
            jotDto.setTags(tags);

            List<MoodDto> moods = new ArrayList<>();
            ResultSet jotMoods = CrudUtil.execute(
                    "SELECT * FROM mood m" +
                    "JOIN jot_mood jm ON m.mood_id = jm.mood_id " +
                    "WHERE jm.jot_id = ?", id);
            while (jotMoods.next()) {
                moods.add(new MoodDto(jotMoods.getString("mood_id"), jotMoods.getString("description")));
            }
            jotDto.setMoods(moods);

            return jotDto;
        }
        return null;
    }

    @Override
    public List<JotDto> getAllJot(String userId) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM jot WHERE user_id=?", userId);
        List<JotDto> jotDtos = new ArrayList<>();
        while (resultSet.next()) {
            JotDto jotDto = new JotDto();
            jotDto.setId(resultSet.getString("jot_id"));
            jotDto.setUserId(resultSet.getString("user_id"));
            jotDto.setTitle(resultSet.getString("title"));
            jotDto.setPath(resultSet.getString("path"));
            jotDto.setCategory(categoryModel.getCategory(resultSet.getString("category_id")));
            jotDto.setLocation(locationModel.getLocation(resultSet.getString("location_id")));
            //get join queries if there is a performance issue
            jotDto.setCreatedAt(resultSet.getTimestamp("created_at"));
            jotDto.setUpdatedAt(resultSet.getTimestamp("updated_at"));

            List<TagDto> tags = new ArrayList<>();
            ResultSet tagResultSet  = CrudUtil.execute(
                    "SELECT * FROM tag t" +
                            "JOIN jot_tag jt ON t.tag_id = jt.tag_id " +
                            "WHERE jt.jot_id = ?", jotDto.getId());
            while (tagResultSet .next()) {
                tags.add(new TagDto(tagResultSet .getString("tag_id"), tagResultSet .getString("name")));
            }
            jotDto.setTags(tags);

            List<MoodDto> moods = new ArrayList<>();
            ResultSet jotMoods = CrudUtil.execute(
                    "SELECT * FROM mood m" +
                            "JOIN jot_mood jm ON m.mood_id = jm.mood_id " +
                            "WHERE jm.jot_id = ?", jotDto.getId());
            while (jotMoods.next()) {
                moods.add(new MoodDto(jotMoods.getString("mood_id"), jotMoods.getString("description")));
            }
            jotDto.setMoods(moods);
            jotDtos.add(jotDto);
        }
        return jotDtos;
    }

    @Override
    public boolean deleteJot(JotDto jotDto) throws Exception {
        return CrudUtil.execute("DELETE FROM jot WHERE jot_id = ?", jotDto.getId());
        //on update and on delete cascades are there in the database
    }
}