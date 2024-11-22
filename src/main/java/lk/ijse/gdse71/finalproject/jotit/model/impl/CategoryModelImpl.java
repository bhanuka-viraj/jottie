package lk.ijse.gdse71.finalproject.jotit.model.impl;

import lk.ijse.gdse71.finalproject.jotit.controller.LoginController;
import lk.ijse.gdse71.finalproject.jotit.dto.CategoryDto;
import lk.ijse.gdse71.finalproject.jotit.model.CategoryModel;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryModelImpl implements CategoryModel {
    @Override
    public boolean saveCategory(CategoryDto category) throws Exception {
        return CrudUtil.execute("INSERT INTO category VALUES (?,?,?)",
                category.getId(),
                category.getDescription(),
                category.getUserId());
    }

    //userid should be passed to the category,
    @Override
    public CategoryDto getCategory(String id,String userId) throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM category WHERE category_id =? AND created_by = ?", id, userId);
        if (rs.next()) {
            return new CategoryDto(rs.getString("category_id"), rs.getString("description"),rs.getString("created_by"));
        } else {
            return null;
        }
    }

    @Override
    public List<CategoryDto> getAllCategories(String userId) throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM category WHERE created_by = ?",userId);
        List<CategoryDto> categories = new ArrayList<>();
        while (rs.next()) {
            categories.add(new CategoryDto(rs.getString("category_id"), rs.getString("description"), rs.getString("created_by")));
        }
        return categories;
    }

    @Override
    public boolean deleteCategory(CategoryDto category) throws Exception {
        return false;
    }


}
