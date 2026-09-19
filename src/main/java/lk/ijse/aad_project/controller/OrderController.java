package lk.ijse.aad_project.controller;

import lk.ijse.aad_project.contant.CommonResponse;
import lk.ijse.aad_project.dto.OrderDTO;
import lk.ijse.aad_project.service.OrderService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping("v1/orders")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Customer creates order
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse saveOrder(
            @RequestBody OrderDTO orderDTO
    ) {

        orderService.saveOrder(orderDTO);

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }

    // Get all orders - Admin can use this
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<OrderDTO> getAllOrders() {

        return orderService.getAllOrders();
    }

    // Get one order
    @GetMapping(
            value = "/{orderId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public OrderDTO getOrderById(
            @PathVariable long orderId
    ) {

        return orderService.getOrderById(orderId);
    }

    // Full order update
    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse updateOrder(
            @RequestBody OrderDTO orderDTO
    ) {

        orderService.updateOrder(orderDTO);

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }

    // Admin updates only order status
    @PatchMapping(
            value = "/{orderId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse updateOrderStatus(
            @PathVariable long orderId,
            @RequestBody OrderDTO orderDTO
    ) {

        orderDTO.setOrderId(orderId);

        orderService.updateOrderStatus(orderDTO);

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }

    // Remove order
    @DeleteMapping(
            value = "/{orderId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse removeOrder(
            @PathVariable long orderId
    ) {

        orderService.removeOrder(orderId);

        return new CommonResponse(
                OPERATION_SUCCESS,
                SUCCESS_MESSAGE
        );
    }
}