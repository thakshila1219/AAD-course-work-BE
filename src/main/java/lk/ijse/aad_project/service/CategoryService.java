package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.CategoryDTO;
 
public interface CategoryService {
    void saveCategory(CategoryDTO categoryDTO);
    void updateCategory(CategoryDTO categoryDTO);
    void removeCategory(long categoryId);
}
