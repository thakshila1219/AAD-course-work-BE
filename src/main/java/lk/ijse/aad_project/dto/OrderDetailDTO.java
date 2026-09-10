package lk.ijse.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private long orderDetailId;
    private int quantity;
    private double unitPrice;
    private long orderId;
    private long menuItemId;

    public OrderDetailDTO(int quantity, double unitPrice, long orderId, long menuItemId) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
    }
}