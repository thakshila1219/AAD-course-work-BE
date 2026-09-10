package lk.ijse.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;  
import lombok.NoArgsConstructor; 

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDTO {
    private long ingredientId;
    private String name;
    private double quantityOnHand;
    private String unit;
    private long supplierId;

    public IngredientDTO(String name, double quantityOnHand, String unit, long supplierId) {
        this.name = name;
        this.quantityOnHand = quantityOnHand;
        this.unit = unit;
        this.supplierId = supplierId;
    }
}
