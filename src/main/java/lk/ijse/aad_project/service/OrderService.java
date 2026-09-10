package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.OrderDTO;

public interface OrderService {
    void saveOrder(OrderDTO orderDTO);
    void updateOrder(OrderDTO orderDTO); 
    void removeOrder(long orderId);

    void updateOrderStatus(long orderId);
}
