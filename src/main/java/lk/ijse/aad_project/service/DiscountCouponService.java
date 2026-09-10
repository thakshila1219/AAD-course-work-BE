package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.DiscountCouponDTO;

public interface DiscountCouponService {
    void saveDiscountCoupon(DiscountCouponDTO discountCouponDTO);
    void updateDiscountCoupon(DiscountCouponDTO discountCouponDTO);
    void removeDiscountCoupon(long couponId);
}