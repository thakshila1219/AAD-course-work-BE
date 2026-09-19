package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {
    void saveCategory(CategoryDTO categoryDTO);
    void updateCategory(CategoryDTO categoryDTO);
    void removeCategory(long categoryId);
    List<CategoryDTO> getAllCategories(); // මේ පේළිය එකතු කරන්න
}