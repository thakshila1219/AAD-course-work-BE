package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.IngredientDTO;

public interface IngredientService {
    void saveIngredient(IngredientDTO ingredientDTO);
    void updateIngredient(IngredientDTO ingredientDTO);
    void removeIngredient(long ingredientId);
} 
