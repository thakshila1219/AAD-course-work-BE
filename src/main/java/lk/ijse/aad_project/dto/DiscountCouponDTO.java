package lk.ijse.aad_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCouponDTO {
    private long couponId;
    private String code;
    private double discountPercentage;
    private LocalDate expiryDate;
    private String status;

    public DiscountCouponDTO(String code, double discountPercentage, LocalDate expiryDate, String status) {
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.expiryDate = expiryDate;
        this.status = status;
    }
}