package lk.ijse.gdse71.finalproject.jotit.dao.custom;


import lk.ijse.gdse71.finalproject.jotit.dao.SuperDao;
import lk.ijse.gdse71.finalproject.jotit.entity.Category;

import java.util.List;

public interface CategoryDAO extends SuperDao {
    public boolean saveCategory(Category category) throws Exception;
    public Category getCategory(String id,String userId)throws Exception;
    public List<Category> getAllCategories(String userId)throws Exception;
    public boolean deleteCategory(Category category)throws Exception;
}
