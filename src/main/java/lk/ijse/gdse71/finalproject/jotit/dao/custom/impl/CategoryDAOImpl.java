package lk.ijse.gdse71.finalproject.jotit.dao.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dao.custom.CategoryDAO;
import lk.ijse.gdse71.finalproject.jotit.entity.Category;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
    @Override
    public boolean saveCategory(Category category) throws Exception {
        return CrudUtil.execute("INSERT INTO category VALUES (?,?,?)",
                category.getId(),
                category.getDescription(),
                category.getUserId());
    }

    @Override
    public Category getCategory(String id,String userId) throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM category WHERE category_id =? AND created_by = ?", id, userId);
        if (rs.next()) {
            return new Category(rs.getString("category_id"), rs.getString("description"),rs.getString("created_by"));
        } else {
            return null;
        }
    }

    @Override
    public List<Category> getAllCategories(String userId) throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM category WHERE created_by = ?",userId);
        List<Category> categories = new ArrayList<>();
        while (rs.next()) {
            categories.add(new Category(rs.getString("category_id"), rs.getString("description"), rs.getString("created_by")));
        }
        return categories;
    }

    @Override
    public boolean deleteCategory(Category category) throws Exception {
        return false;
    }


}
