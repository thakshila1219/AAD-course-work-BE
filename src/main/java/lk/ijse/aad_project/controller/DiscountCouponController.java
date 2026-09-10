package lk.ijse.aad_project.controller;

import lk.ijse.aad_project.dto.DiscountCouponDTO;
import lk.ijse.aad_project.service.DiscountCouponService; 
import lk.ijse.aad_project.contant.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping(value = "v1/discount-coupons")
public class DiscountCouponController {

    private final DiscountCouponService discountCouponService;

    public DiscountCouponController(DiscountCouponService discountCouponService) {
        this.discountCouponService = discountCouponService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveDiscountCoupon(@RequestBody DiscountCouponDTO discountCouponDTO){
        discountCouponService.saveDiscountCoupon(discountCouponDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateDiscountCoupon(@RequestBody DiscountCouponDTO discountCouponDTO){
        discountCouponService.updateDiscountCoupon(discountCouponDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @DeleteMapping(value = "/{couponId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse removeDiscountCoupon(@PathVariable long couponId){
        discountCouponService.removeDiscountCoupon(couponId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}
