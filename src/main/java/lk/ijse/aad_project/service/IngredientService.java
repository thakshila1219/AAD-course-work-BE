package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.IngredientDTO;

import java.util.List;

public interface IngredientService {

    // Save Ingredient
    void saveIngredient(IngredientDTO ingredientDTO);

    // Update Ingredient
    void updateIngredient(IngredientDTO ingredientDTO);

    // Delete Ingredient
    void removeIngredient(long ingredientId);

    // Get All Ingredients
    List<IngredientDTO> getAllIngredients();
}