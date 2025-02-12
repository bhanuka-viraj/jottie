package lk.ijse.gdse71.finalproject.jotit.service.custom;


import lk.ijse.gdse71.finalproject.jotit.dto.CategoryDto;
import lk.ijse.gdse71.finalproject.jotit.service.SuperService;

import java.util.List;

public interface CategoryService extends SuperService {
    public boolean saveCategory(CategoryDto categoryDto) throws Exception;
    public CategoryDto getCategory(String id,String userId)throws Exception;
    public List<CategoryDto> getAllCategories(String userId)throws Exception;
    public boolean deleteCategory(CategoryDto categoryDto)throws Exception;
}
