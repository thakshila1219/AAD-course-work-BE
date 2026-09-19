package lk.ijse.aad_project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderDTO {

    private long orderId;

    private LocalDateTime orderDate;

    private double totalAmount;

    private String status;

    private long userId;

    private long tableId;

    private long discountCouponId;

    // Display purposes
    private String username;

    private String tableNumber;

    public OrderDTO(
            LocalDateTime orderDate,
            double totalAmount,
            String status,
            long userId,
            long tableId,
            long discountCouponId
    ) {
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.userId = userId;
        this.tableId = tableId;
        this.discountCouponId = discountCouponId;
    }

    public OrderDTO(
            long orderId,
            LocalDateTime orderDate,
            double totalAmount,
            String status,
            long userId,
            long tableId,
            long discountCouponId,
            String username,
            String tableNumber
    ) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.userId = userId;
        this.tableId = tableId;
        this.discountCouponId = discountCouponId;
        this.username = username;
        this.tableNumber = tableNumber;
    }
}