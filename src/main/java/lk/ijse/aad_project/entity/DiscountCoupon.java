package lk.ijse.aad_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor; 
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DiscountCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long couponId;
    private String code;
    private double discountPercentage;
    private LocalDate expiryDate;
    private String status;

    @OneToMany(mappedBy = "discountCoupon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orderList;
}
