package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.DiscountCouponDTO;
import lk.ijse.aad_project.entity.DiscountCoupon; 
import lk.ijse.aad_project.repository.DiscountCouponRepository;
import lk.ijse.aad_project.service.DiscountCouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class DiscountCouponServiceImpl implements DiscountCouponService {

    private final DiscountCouponRepository discountCouponRepository;

    public DiscountCouponServiceImpl(DiscountCouponRepository discountCouponRepository) {
        this.discountCouponRepository = discountCouponRepository;
    }

    @Override
    public void saveDiscountCoupon(DiscountCouponDTO discountCouponDTO) {
        log.info("Execute method saveDiscountCoupon");
        try {
            DiscountCoupon discountCoupon = new DiscountCoupon();
            discountCoupon.setCode(discountCouponDTO.getCode());
            discountCoupon.setDiscountPercentage(discountCouponDTO.getDiscountPercentage());
            discountCoupon.setExpiryDate(discountCouponDTO.getExpiryDate());
            discountCoupon.setStatus(discountCouponDTO.getStatus());

            discountCouponRepository.save(discountCoupon);
        } catch (Exception e) {
            log.error("Error in saveDiscountCoupon : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateDiscountCoupon(DiscountCouponDTO discountCouponDTO) {
        log.info("Execute method updateDiscountCoupon");
        try {
            Optional<DiscountCoupon> optionalDiscountCoupon = discountCouponRepository.findById(discountCouponDTO.getCouponId());
            if (optionalDiscountCoupon.isEmpty())
                throw new RuntimeException("Sorry, related discount coupon is not found.");

            DiscountCoupon discountCoupon = optionalDiscountCoupon.get();
            discountCoupon.setCode(discountCouponDTO.getCode());
            discountCoupon.setDiscountPercentage(discountCouponDTO.getDiscountPercentage());
            discountCoupon.setExpiryDate(discountCouponDTO.getExpiryDate());
            discountCoupon.setStatus(discountCouponDTO.getStatus());

            discountCouponRepository.save(discountCoupon);
        } catch (Exception e) {
            log.error("Error in updateDiscountCoupon : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void removeDiscountCoupon(long couponId) {
        log.info("Execute method removeDiscountCoupon");
        try {
            Optional<DiscountCoupon> optionalDiscountCoupon = discountCouponRepository.findById(couponId);
            if (optionalDiscountCoupon.isEmpty())
                throw new RuntimeException("Sorry, related discount coupon is not found.");

            discountCouponRepository.deleteById(couponId);
        } catch (Exception e) {
            log.error("Error in removeDiscountCoupon : " + e.getMessage());
            throw e;
        }
    }
}
