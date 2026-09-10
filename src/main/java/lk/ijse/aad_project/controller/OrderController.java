package lk.ijse.aad_project.controller;
 
import lk.ijse.aad_project.dto.OrderDTO;
import lk.ijse.aad_project.service.OrderService;
import lk.ijse.aad_project.contant.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping(value = "v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse saveOrder(@RequestBody OrderDTO orderDTO){
        orderService.saveOrder(orderDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateOrder(@RequestBody OrderDTO orderDTO){
        orderService.updateOrder(orderDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @PatchMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updateOrderStatus(@PathVariable long orderId){
        orderService.updateOrderStatus(orderId);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}
