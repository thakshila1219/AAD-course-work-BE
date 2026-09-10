package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.RecipeItemDTO;
import lk.ijse.aad_project.entity.Ingredient;
import lk.ijse.aad_project.entity.MenuItem;
import lk.ijse.aad_project.entity.RecipeItem;
import lk.ijse.aad_project.repository.IngredientRepository;
import lk.ijse.aad_project.repository.MenuItemRepository;
import lk.ijse.aad_project.repository.RecipeItemRepository; 
import lk.ijse.aad_project.service.RecipeItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class RecipeItemServiceImpl implements RecipeItemService {

    private final RecipeItemRepository recipeItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final IngredientRepository ingredientRepository;

    public RecipeItemServiceImpl(RecipeItemRepository recipeItemRepository, MenuItemRepository menuItemRepository, IngredientRepository ingredientRepository) {
        this.recipeItemRepository = recipeItemRepository;
        this.menuItemRepository = menuItemRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void saveRecipeItem(RecipeItemDTO recipeItemDTO) {
        log.info("Execute method saveRecipeItem");
        try {
            RecipeItem recipeItem = new RecipeItem();
            recipeItem.setRequiredQuantity(recipeItemDTO.getRequiredQuantity());

            Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(recipeItemDTO.getMenuItemId());
            if (optionalMenuItem.isEmpty())
                throw new RuntimeException("Sorry, related menu item is not found.");
            recipeItem.setMenuItem(optionalMenuItem.get());

            Optional<Ingredient> optionalIngredient = ingredientRepository.findById(recipeItemDTO.getIngredientId());
            if (optionalIngredient.isEmpty())
                throw new RuntimeException("Sorry, related ingredient is not found.");
            recipeItem.setIngredient(optionalIngredient.get());

            recipeItemRepository.save(recipeItem);
        } catch (Exception e) {
            log.error("Error in saveRecipeItem : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateRecipeItem(RecipeItemDTO recipeItemDTO) {
        log.info("Execute method updateRecipeItem");
        try {
            Optional<RecipeItem> optionalRecipeItem = recipeItemRepository.findById(recipeItemDTO.getRecipeItemId());
            if (optionalRecipeItem.isEmpty())
                throw new RuntimeException("Sorry, related recipe item is not found.");

            RecipeItem recipeItem = optionalRecipeItem.get();
            recipeItem.setRequiredQuantity(recipeItemDTO.getRequiredQuantity());

            Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(recipeItemDTO.getMenuItemId());
            if (optionalMenuItem.isEmpty())
                throw new RuntimeException("Sorry, related menu item is not found.");
            recipeItem.setMenuItem(optionalMenuItem.get());

            Optional<Ingredient> optionalIngredient = ingredientRepository.findById(recipeItemDTO.getIngredientId());
            if (optionalIngredient.isEmpty())
                throw new RuntimeException("Sorry, related ingredient is not found.");
            recipeItem.setIngredient(optionalIngredient.get());

            recipeItemRepository.save(recipeItem);
        } catch (Exception e) {
            log.error("Error in updateRecipeItem : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void removeRecipeItem(long recipeItemId) {
        log.info("Execute method removeRecipeItem");
        try {
            Optional<RecipeItem> optionalRecipeItem = recipeItemRepository.findById(recipeItemId);
            if (optionalRecipeItem.isEmpty())
                throw new RuntimeException("Sorry, related recipe item is not found.");

            recipeItemRepository.deleteById(recipeItemId);
        } catch (Exception e) {
            log.error("Error in removeRecipeItem : " + e.getMessage());
            throw e;
        }
    }
}
