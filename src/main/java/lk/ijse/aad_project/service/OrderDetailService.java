package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {

    void saveOrderDetail(OrderDetailDTO orderDetailDTO);

    void updateOrderDetail(OrderDetailDTO orderDetailDTO);

    void removeOrderDetail(long orderDetailId);

    List<OrderDetailDTO> getAllOrderDetails();

    List<OrderDetailDTO> getOrderDetailsByOrderId(long orderId);
}