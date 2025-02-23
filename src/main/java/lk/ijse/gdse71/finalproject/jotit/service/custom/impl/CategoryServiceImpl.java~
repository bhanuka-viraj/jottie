package lk.ijse.gdse71.finalproject.jotit.service.custom.impl;

import lk.ijse.gdse71.finalproject.jotit.dao.custom.CategoryDAO;
import lk.ijse.gdse71.finalproject.jotit.dao.custom.impl.CategoryDAOImpl;
import lk.ijse.gdse71.finalproject.jotit.dto.CategoryDto;
import lk.ijse.gdse71.finalproject.jotit.entity.Category;
import lk.ijse.gdse71.finalproject.jotit.service.custom.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    public final CategoryDAO categoryDAO = new CategoryDAOImpl();
    @Override
    public boolean saveCategory(CategoryDto categoryDto) throws Exception {

        return categoryDAO.saveCategory(
                new Category(
                        categoryDto.getId(),
                        categoryDto.getDescription(),
                        categoryDto.getUserId())
        );
    }

    @Override
    public CategoryDto getCategory(String id, String userId) throws Exception {
        Category category = categoryDAO.getCategory(id, userId);
        return new CategoryDto(category.getId(),category.getDescription(),category.getUserId());
    }

    @Override
    public List<CategoryDto> getAllCategories(String userId) throws Exception {
        return categoryDAO.getAllCategories(userId).stream().map(c ->
                new CategoryDto(c.getId(), c.getDescription(), c.getUserId())
        ).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public boolean deleteCategory(CategoryDto categoryDto) throws Exception {
        return categoryDAO.deleteCategory(new Category(categoryDto.getId(),categoryDto.getDescription(),categoryDto.getUserId()));
    }
}
