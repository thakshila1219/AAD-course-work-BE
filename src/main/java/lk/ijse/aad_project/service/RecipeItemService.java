package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.RecipeItemDTO;

public interface RecipeItemService {
    void saveRecipeItem(RecipeItemDTO recipeItemDTO);
    void updateRecipeItem(RecipeItemDTO recipeItemDTO);
    void removeRecipeItem(long recipeItemId); 
}
