package lk.ijse.aad_project.repository;

import lk.ijse.aad_project.entity.DiscountCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountCouponRepository
        extends JpaRepository<DiscountCoupon, Long> {
}