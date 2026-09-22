package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    long saveOrder(OrderDTO orderDTO);
    
    void updateOrder(OrderDTO orderDTO);

    void removeOrder(long orderId);

    void updateOrderStatus(OrderDTO orderDTO);

    List<OrderDTO> getAllOrders();

    OrderDTO getOrderById(long orderId);
}