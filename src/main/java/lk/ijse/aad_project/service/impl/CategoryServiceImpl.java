package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.CategoryDTO;
import lk.ijse.aad_project.entity.Category;
import lk.ijse.aad_project.repository.CategoryRepository;
import lk.ijse.aad_project.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service; 

import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void saveCategory(CategoryDTO categoryDTO) {
        log.info("Execute method saveCategory");
        try {
            Category category = new Category();
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setDescription(categoryDTO.getDescription());

            categoryRepository.save(category);
        } catch (Exception e) {
            log.error("Error in saveCategory : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        log.info("Execute method updateCategory");
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDTO.getCategoryId());
            if (optionalCategory.isEmpty())
                throw new RuntimeException("Sorry, related category is not found.");

            Category category = optionalCategory.get();
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setDescription(categoryDTO.getDescription());

            categoryRepository.save(category);
        } catch (Exception e) {
            log.error("Error in updateCategory : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void removeCategory(long categoryId) {
        log.info("Execute method removeCategory");
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            if (optionalCategory.isEmpty())
                throw new RuntimeException("Sorry, related category is not found.");

            categoryRepository.deleteById(categoryId);
        } catch (Exception e) {
            log.error("Error in removeCategory : " + e.getMessage());
            throw e;
        }
    }
}
