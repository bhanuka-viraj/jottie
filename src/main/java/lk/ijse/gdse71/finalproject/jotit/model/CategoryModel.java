package lk.ijse.gdse71.finalproject.jotit.model;

import lk.ijse.gdse71.finalproject.jotit.dto.CategoryDto;

import java.util.List;

public interface CategoryModel {
    public boolean saveCategory(CategoryDto category) throws Exception;
    public CategoryDto getCategory(String id)throws Exception;
    public List<CategoryDto> getAllCategories()throws Exception;
    public boolean deleteCategory(CategoryDto category)throws Exception;

}
