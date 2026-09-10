package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.OrderDetailDTO;
import lk.ijse.aad_project.entity.MenuItem;
import lk.ijse.aad_project.entity.Order;
import lk.ijse.aad_project.entity.OrderDetail;
import lk.ijse.aad_project.repository.MenuItemRepository;
import lk.ijse.aad_project.repository.OrderDetailRepository;
import lk.ijse.aad_project.repository.OrderRepository;
import lk.ijse.aad_project.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, MenuItemRepository menuItemRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public void saveOrderDetail(OrderDetailDTO orderDetailDTO) {
        log.info("Execute method saveOrderDetail");
        try {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(orderDetailDTO.getQuantity());
            orderDetail.setUnitPrice(orderDetailDTO.getUnitPrice());

            Optional<Order> optionalOrder = orderRepository.findById(orderDetailDTO.getOrderId());
            if (optionalOrder.isEmpty())
                throw new RuntimeException("Sorry, related order is not found.");
            orderDetail.setOrder(optionalOrder.get());

            Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(orderDetailDTO.getMenuItemId());
            if (optionalMenuItem.isEmpty())
                throw new RuntimeException("Sorry, related menu item is not found.");
            orderDetail.setMenuItem(optionalMenuItem.get());

            orderDetailRepository.save(orderDetail);
        } catch (Exception e) {
            log.error("Error in saveOrderDetail : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateOrderDetail(OrderDetailDTO orderDetailDTO) {
        log.info("Execute method updateOrderDetail");
        try {
            Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findById(orderDetailDTO.getOrderDetailId());
            if (optionalOrderDetail.isEmpty())
                throw new RuntimeException("Sorry, related order detail is not found.");

            OrderDetail orderDetail = optionalOrderDetail.get();
            orderDetail.setQuantity(orderDetailDTO.getQuantity());
            orderDetail.setUnitPrice(orderDetailDTO.getUnitPrice());

            Optional<Order> optionalOrder = orderRepository.findById(orderDetailDTO.getOrderId());
            if (optionalOrder.isEmpty())
                throw new RuntimeException("Sorry, related order is not found.");
            orderDetail.setOrder(optionalOrder.get());

            Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(orderDetailDTO.getMenuItemId());
            if (optionalMenuItem.isEmpty())
                throw new RuntimeException("Sorry, related menu item is not found.");
            orderDetail.setMenuItem(optionalMenuItem.get());

            orderDetailRepository.save(orderDetail);
        } catch (Exception e) {
            log.error("Error in updateOrderDetail : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void removeOrderDetail(long orderDetailId) {
        log.info("Execute method removeOrderDetail");
        try {
            Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findById(orderDetailId);
            if (optionalOrderDetail.isEmpty())
                throw new RuntimeException("Sorry, related order detail is not found.");

            orderDetailRepository.deleteById(orderDetailId);
        } catch (Exception e) {
            log.error("Error in removeOrderDetail : " + e.getMessage());
            throw e;
        }
    }
}