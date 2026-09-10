package lk.ijse.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private long orderId;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String status;
    private long userId;
    private long discountCouponId;

    public OrderDTO(LocalDateTime orderDate, double totalAmount, String status, long userId, long discountCouponId) {
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.userId = userId;
        this.discountCouponId = discountCouponId;
    }
}