package lk.ijse.gdse71.finalproject.jotit.dao.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dao.custom.JotDAO;
import lk.ijse.gdse71.finalproject.jotit.entity.Jot;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JotDAOImpl implements JotDAO {

    @Override
    public boolean saveJot(Jot jot) throws Exception {
            return CrudUtil.execute(
                    "INSERT INTO jot (jot_id, user_id, title, path, category_id, location_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    jot.getId(),
                    jot.getUserId(),
                    jot.getTitle(),
                    jot.getPath(),
                    jot.getCategoryId(),
                    jot.getLocationId()
            );

    }

    public boolean updateJot(Jot jot) throws Exception {
            return CrudUtil.execute(
                    "UPDATE jot SET title = ?, path = ?, category_id = ?, location_id = ? WHERE jot_id = ? AND user_id = ?",
                    jot.getTitle(),
                    jot.getPath(),
                    jot.getCategoryId(),
                    jot.getLocationId(),
                    jot.getId(),
                    jot.getUserId()
            );

    }

    @Override
    public Jot getJot(String id) throws Exception {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT j.jot_id, j.user_id, j.title, j.path, j.created_at, j.updated_at, " +
                        "c.category_id, c.description AS category_description, " +
                        "l.location_id, l.description AS location_description " +
                        "FROM jot j " +
                        "JOIN category c ON j.category_id = c.category_id " +
                        "JOIN location l ON j.location_id = l.location_id " +
                        "WHERE j.jot_id = ?", id);

        if (resultSet.next()) {
            Jot jot = new Jot();
            jot.setId(resultSet.getString("jot_id"));
            jot.setUserId(resultSet.getString("user_id"));
            jot.setTitle(resultSet.getString("title"));
            jot.setPath(resultSet.getString("path"));

            jot.setCategoryId(resultSet.getString("category_id"));
            jot.setLocationId(resultSet.getString("location_id"));

            jot.setCreatedAt(resultSet.getTimestamp("created_at"));
            jot.setUpdatedAt(resultSet.getTimestamp("updated_at"));

            return jot;
        }
        return null;
    }

    @Override
    public List<Jot> getAllJot(String userId) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM jot WHERE user_id=? ORDER BY created_at DESC",userId);

        return mapResultSetToJotDtoList(resultSet);
    }

    @Override
    public boolean deleteJot(Jot jot) throws Exception {
        return CrudUtil.execute("DELETE FROM jot WHERE jot_id = ?", jot.getId());
        //on update and on delete cascades are there in the database
    }

    @Override
    public List<Jot> findJots(String text, String userId) throws Exception {
        if (text.startsWith("#")) {
            text = text.replace("#", "");
            return findJotsByTag(text,userId);
        }
        if (text.startsWith("cat@")){
            text = text.replace("cat@", "");
            return findJotsByCategory(text,userId);
        }
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM jot WHERE user_id = ? AND  title LIKE ? ",userId, "%" + text + "%"
        );

        return mapResultSetToJotDtoList(resultSet);
    }

    private List<Jot> findJotsByCategory(String categoryName, String userId) throws Exception {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT j.* FROM jot j " +
                        "JOIN category c ON j.category_id = c.category_id " +
                        "WHERE c.description = ? AND j.user_id = ?", categoryName,userId
        );

        return mapResultSetToJotDtoList(resultSet);
    }

    private List<Jot> findJotsByTag(String tagName, String userId) throws Exception {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT j.* FROM jot j " +
                        " JOIN jot_tag jt ON j.jot_id = jt.jot_id " +
                        " JOIN tag t ON jt.tag_id = t.tag_id " +
                        " WHERE t.name LIKE ? AND j.user_id = ?", "%" + tagName + "%",userId
        );
        return mapResultSetToJotDtoList(resultSet);
    }


    @Override
    public int getJotCountByCategory(String categoryId,String userId) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(*) FROM jot WHERE category_id = ? AND user_id = ?", categoryId,userId);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
    private List<Jot> mapResultSetToJotDtoList(ResultSet resultSet) throws Exception {
        List<Jot> jots = new ArrayList<>();
        while (resultSet.next()) {
            Jot jot = new Jot();
            jot.setId(resultSet.getString("jot_id"));
            jot.setUserId(resultSet.getString("user_id"));
            jot.setTitle(resultSet.getString("title"));
            jot.setPath(resultSet.getString("path"));
            jot.setCreatedAt(resultSet.getTimestamp("created_at"));
            jot.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            jot.setCategoryId(resultSet.getString("category_id"));
            jot.setLocationId(resultSet.getString("location_id"));
            jots.add(jot);

            //can get only category id and location id into the entity and then process the
            // actual ones with dtos
        }
        return jots;
    }
}