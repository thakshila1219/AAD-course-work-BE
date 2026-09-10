package lk.ijse.aad_project.dto;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeItemDTO {
    private long recipeItemId;
    private double requiredQuantity;
    private long menuItemId;
    private long ingredientId;

    public RecipeItemDTO(double requiredQuantity, long menuItemId, long ingredientId) {
        this.requiredQuantity = requiredQuantity;
        this.menuItemId = menuItemId;
        this.ingredientId = ingredientId;
    }
}
