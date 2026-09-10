package lk.ijse.aad_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RecipeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recipeItemId;
    private double requiredQuantity;

    @ManyToOne
    private MenuItem menuItem;

    @ManyToOne
    private Ingredient ingredient;
}