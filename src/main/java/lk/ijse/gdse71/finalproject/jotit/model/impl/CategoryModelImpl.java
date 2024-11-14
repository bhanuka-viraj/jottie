package lk.ijse.gdse71.finalproject.jotit.model.impl;

import lk.ijse.gdse71.finalproject.jotit.dto.CategoryDto;
import lk.ijse.gdse71.finalproject.jotit.model.CategoryModel;
import lk.ijse.gdse71.finalproject.jotit.util.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryModelImpl implements CategoryModel {
    @Override
    public boolean saveCategory(CategoryDto category) throws Exception {
        return CrudUtil.execute("INSERT INTO category VALUES (?,?)",
                category.getId(),
                category.getDescription());
    }

    @Override
    public CategoryDto getCategory(String id) throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM category WHERE category_id =?", id);
        return new CategoryDto(rs.getString("category_id"), rs.getString("description"));
    }

    @Override
    public List<CategoryDto> getAllCategories() throws Exception {
        ResultSet rs = CrudUtil.execute("SELECT * FROM category");
        List<CategoryDto> categories = new ArrayList<>();
        while (rs.next()) {
            categories.add(new CategoryDto(rs.getString("category_id"), rs.getString("description")));
        }
        return categories;
    }

    @Override
    public boolean deleteCategory(CategoryDto category) throws Exception {
        return false;
    }
}
