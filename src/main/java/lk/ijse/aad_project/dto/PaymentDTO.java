package lk.ijse.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private long paymentId;
    private double amount;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private long orderId;

    public PaymentDTO(double amount, LocalDateTime paymentDate, String paymentMethod, long orderId) {
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.orderId = orderId;
    }
}