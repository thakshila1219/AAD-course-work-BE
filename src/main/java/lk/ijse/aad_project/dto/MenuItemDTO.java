package lk.ijse.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDTO {
    private long itemId;
    private String name;
    private double price;
    private String description;
    private long categoryId;

    public MenuItemDTO(String name, double price, String description, long categoryId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }
}
