package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.OrderDTO;
import lk.ijse.aad_project.entity.DiscountCoupon;
import lk.ijse.aad_project.entity.Order; 
import lk.ijse.aad_project.entity.User;
import lk.ijse.aad_project.repository.DiscountCouponRepository;
import lk.ijse.aad_project.repository.OrderRepository;
import lk.ijse.aad_project.repository.UserRepository;
import lk.ijse.aad_project.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DiscountCouponRepository discountCouponRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, DiscountCouponRepository discountCouponRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.discountCouponRepository = discountCouponRepository;
    }

    @Override
    public void saveOrder(OrderDTO orderDTO) {
        log.info("Execute method saveOrder");
        try {
            Order order = new Order();
            order.setOrderDate(orderDTO.getOrderDate());
            order.setTotalAmount(orderDTO.getTotalAmount());
            order.setStatus(orderDTO.getStatus());

            Optional<User> optionalUser = userRepository.findById(orderDTO.getUserId());
            if (optionalUser.isEmpty())
                throw new RuntimeException("Sorry, related user is not found.");
            order.setUser(optionalUser.get());

            if (orderDTO.getDiscountCouponId() > 0) {
                Optional<DiscountCoupon> optionalCoupon = discountCouponRepository.findById(orderDTO.getDiscountCouponId());
                if (optionalCoupon.isEmpty())
                    throw new RuntimeException("Sorry, related discount coupon is not found.");
                order.setDiscountCoupon(optionalCoupon.get());
            }

            orderRepository.save(order);
        } catch (Exception e) {
            log.error("Error in saveOrder : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateOrder(OrderDTO orderDTO) {
        log.info("Execute method updateOrder");
        try {
            Optional<Order> optionalOrder = orderRepository.findById(orderDTO.getOrderId());
            if (optionalOrder.isEmpty())
                throw new RuntimeException("Sorry, related order is not found.");

            Order order = optionalOrder.get();
            order.setOrderDate(orderDTO.getOrderDate());
            order.setTotalAmount(orderDTO.getTotalAmount());
            order.setStatus(orderDTO.getStatus());

            Optional<User> optionalUser = userRepository.findById(orderDTO.getUserId());
            if (optionalUser.isEmpty())
                throw new RuntimeException("Sorry, related user is not found.");
            order.setUser(optionalUser.get());

            if (orderDTO.getDiscountCouponId() > 0) {
                Optional<DiscountCoupon> optionalCoupon = discountCouponRepository.findById(orderDTO.getDiscountCouponId());
                if (optionalCoupon.isEmpty())
                    throw new RuntimeException("Sorry, related discount coupon is not found.");
                order.setDiscountCoupon(optionalCoupon.get());
            }

            orderRepository.save(order);
        } catch (Exception e) {
            log.error("Error in updateOrder : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void removeOrder(long orderId) {
        log.info("Execute method removeOrder");
        try {
            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if (optionalOrder.isEmpty())
                throw new RuntimeException("Sorry, related order is not found.");

            orderRepository.deleteById(orderId);
        } catch (Exception e) {
            log.error("Error in removeOrder : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void updateOrderStatus(long orderId) {
        log.info("Execute method updateOrderStatus");
        try {
            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if (optionalOrder.isEmpty()) {
                throw new RuntimeException("Sorry, related order is not found.");
            }

            Order order = optionalOrder.get();

            if ("PENDING".equalsIgnoreCase(order.getStatus())) {
                order.setStatus("COMPLETED");
            } else {
                order.setStatus("UPDATED");
            }

            orderRepository.save(order);
        } catch (Exception e) {
            log.error("Error in updateOrderStatus : " + e.getMessage());
            throw e;
        }
    }
}
