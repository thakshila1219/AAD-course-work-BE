package lk.ijse.aad_project.controller;

import lk.ijse.aad_project.contant.CommonResponse;
import lk.ijse.aad_project.dto.OrderDetailDTO;
import lk.ijse.aad_project.service.OrderDetailService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping(value = "v1/order-details")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    public OrderDetailController(
            OrderDetailService orderDetailService
    ) {
        this.orderDetailService = orderDetailService;
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse saveOrderDetail(
            @RequestBody OrderDetailDTO orderDetailDTO
    ) {

        orderDetailService.saveOrderDetail(
                orderDetailDTO
        );

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<OrderDetailDTO> getAllOrderDetails() {

        return orderDetailService
                .getAllOrderDetails();
    }

    @GetMapping(
            value = "/order/{orderId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<OrderDetailDTO> getOrderDetailsByOrderId(
            @PathVariable long orderId
    ) {

        return orderDetailService
                .getOrderDetailsByOrderId(orderId);
    }

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse updateOrderDetail(
            @RequestBody OrderDetailDTO orderDetailDTO
    ) {

        orderDetailService.updateOrderDetail(
                orderDetailDTO
        );

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }

    @DeleteMapping(
            value = "/{orderDetailId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse removeOrderDetail(
            @PathVariable long orderDetailId
    ) {

        orderDetailService.removeOrderDetail(
                orderDetailId
        );

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }
}