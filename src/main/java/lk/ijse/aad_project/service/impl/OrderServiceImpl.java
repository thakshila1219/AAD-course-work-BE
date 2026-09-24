package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.OrderDTO;
import lk.ijse.aad_project.entity.DiscountCoupon;
import lk.ijse.aad_project.entity.DiningTable;
import lk.ijse.aad_project.entity.Order;
import lk.ijse.aad_project.entity.User;
import lk.ijse.aad_project.repository.DiscountCouponRepository;
import lk.ijse.aad_project.repository.DiningTableRepository;
import lk.ijse.aad_project.repository.OrderRepository;
import lk.ijse.aad_project.repository.UserRepository;
import lk.ijse.aad_project.service.EmailService;
import lk.ijse.aad_project.service.OrderService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DiningTableRepository diningTableRepository;
    private final DiscountCouponRepository discountCouponRepository;
    private final EmailService emailService;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            UserRepository userRepository,
            DiningTableRepository diningTableRepository,
            DiscountCouponRepository discountCouponRepository,
            EmailService emailService
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.diningTableRepository = diningTableRepository;
        this.discountCouponRepository = discountCouponRepository;
        this.emailService = emailService;
    }

    @Override
    public long saveOrder(OrderDTO orderDTO) {

        log.info("Execute method saveOrder");

        try {

            Order order = new Order();

            order.setOrderDate(orderDTO.getOrderDate());
            order.setTotalAmount(orderDTO.getTotalAmount());

            order.setStatus(
                    orderDTO.getStatus() == null
                            || orderDTO.getStatus().isBlank()
                            ? "PENDING"
                            : orderDTO.getStatus()
            );

            Optional<User> optionalUser =
                    userRepository.findById(orderDTO.getUserId());

            if (optionalUser.isEmpty()) {
                throw new RuntimeException(
                        "Sorry, related user is not found."
                );
            }

            User user = optionalUser.get();

            boolean isCustomer =
                    user.getUserRoleList() != null
                            && user.getUserRoleList()
                            .stream()
                            .anyMatch(userRole ->
                                    userRole.getRole() != null
                                            && "CUSTOMER".equalsIgnoreCase(
                                            userRole.getRole().getRoleName()
                                    )
                            );

            if (!isCustomer) {
                throw new RuntimeException(
                        "Only customers can create orders."
                );
            }

            order.setUser(user);

            if (orderDTO.getTableId() > 0) {

                Optional<DiningTable> optionalTable =
                        diningTableRepository.findById(
                                orderDTO.getTableId()
                        );

                if (optionalTable.isEmpty()) {
                    throw new RuntimeException(
                            "Sorry, related dining table is not found."
                    );
                }

                order.setDiningTable(optionalTable.get());
            }

            if (orderDTO.getDiscountCouponId() > 0) {

                Optional<DiscountCoupon> optionalCoupon =
                        discountCouponRepository.findById(
                                orderDTO.getDiscountCouponId()
                        );

                if (optionalCoupon.isEmpty()) {
                    throw new RuntimeException(
                            "Sorry, related discount coupon is not found."
                    );
                }

                order.setDiscountCoupon(optionalCoupon.get());
            }

            Order savedOrder =
                    orderRepository.save(order);

            return savedOrder.getOrderId();

        } catch (Exception e) {

            log.error(
                    "Error in saveOrder : " + e.getMessage()
            );

            throw e;
        }
    }

    @Override
    public void updateOrder(OrderDTO orderDTO) {

        log.info("Execute method updateOrder");

        try {

            Optional<Order> optionalOrder =
                    orderRepository.findById(
                            orderDTO.getOrderId()
                    );

            if (optionalOrder.isEmpty()) {
                throw new RuntimeException(
                        "Sorry, related order is not found."
                );
            }

            Order order = optionalOrder.get();

            order.setOrderDate(orderDTO.getOrderDate());
            order.setTotalAmount(orderDTO.getTotalAmount());
            order.setStatus(orderDTO.getStatus());

            if (orderDTO.getUserId() > 0) {

                Optional<User> optionalUser =
                        userRepository.findById(
                                orderDTO.getUserId()
                        );

                if (optionalUser.isEmpty()) {
                    throw new RuntimeException(
                            "Sorry, related user is not found."
                    );
                }

                order.setUser(optionalUser.get());
            }

            if (orderDTO.getTableId() > 0) {

                Optional<DiningTable> optionalTable =
                        diningTableRepository.findById(
                                orderDTO.getTableId()
                        );

                if (optionalTable.isEmpty()) {
                    throw new RuntimeException(
                            "Sorry, related dining table is not found."
                    );
                }

                order.setDiningTable(optionalTable.get());
            }

            if (orderDTO.getDiscountCouponId() > 0) {

                Optional<DiscountCoupon> optionalCoupon =
                        discountCouponRepository.findById(
                                orderDTO.getDiscountCouponId()
                        );

                if (optionalCoupon.isEmpty()) {
                    throw new RuntimeException(
                            "Sorry, related discount coupon is not found."
                    );
                }

                order.setDiscountCoupon(
                        optionalCoupon.get()
                );

            } else {

                order.setDiscountCoupon(null);
            }

            orderRepository.save(order);

        } catch (Exception e) {

            log.error(
                    "Error in updateOrder : " + e.getMessage()
            );

            throw e;
        }
    }

    @Override
    @Transactional
    public void updateOrderStatus(OrderDTO orderDTO) {

        log.info("Execute method updateOrderStatus");

        try {

            Optional<Order> optionalOrder =
                    orderRepository.findById(
                            orderDTO.getOrderId()
                    );

            if (optionalOrder.isEmpty()) {
                throw new RuntimeException(
                        "Sorry, related order is not found."
                );
            }

            Order order = optionalOrder.get();

            String oldStatus = order.getStatus();
            String newStatus = orderDTO.getStatus();

            if (newStatus == null || newStatus.isBlank()) {

                throw new RuntimeException(
                        "Order status cannot be empty."
                );
            }

            order.setStatus(newStatus);

            orderRepository.save(order);

            // Check old and new order status
            log.info(
                    "STATUS UPDATE -> Order ID: {}, Old Status: {}, New Status: {}",
                    order.getOrderId(),
                    oldStatus,
                    newStatus
            );

            // Check whether email condition is reached
            log.info(
                    "EMAIL CHECK -> oldStatus={}, newStatus={}",
                    oldStatus,
                    newStatus
            );

            /*
             * Send email only when order changes to CONFIRMED.
             */
            if (!"CONFIRMED".equalsIgnoreCase(oldStatus)
                    && "CONFIRMED".equalsIgnoreCase(newStatus)) {

                log.info(
                        "Order {} changed to CONFIRMED. Sending email...",
                        order.getOrderId()
                );

                try {

                    emailService.sendOrderConfirmationEmail(order);

                } catch (Exception emailException) {

                    /*
                     * Order is already confirmed.
                     * Email failure should not undo the confirmation.
                     */
                    log.error(
                            "Order {} was confirmed, but confirmation email could not be sent: {}",
                            order.getOrderId(),
                            emailException.getMessage()
                    );
                }
            }

        } catch (Exception e) {

            log.error(
                    "Error in updateOrderStatus : "
                            + e.getMessage()
            );

            throw e;
        }
    }

    @Override
    public void removeOrder(long orderId) {

        log.info("Execute method removeOrder");

        try {

            Optional<Order> optionalOrder =
                    orderRepository.findById(orderId);

            if (optionalOrder.isEmpty()) {
                throw new RuntimeException(
                        "Sorry, related order is not found."
                );
            }

            orderRepository.deleteById(orderId);

        } catch (Exception e) {

            log.error(
                    "Error in removeOrder : "
                            + e.getMessage()
            );

            throw e;
        }
    }

    @Override
    public List<OrderDTO> getAllOrders() {

        log.info("Execute method getAllOrders");

        List<Order> orders =
                orderRepository.findAll();

        return orders.stream()
                .map(order -> {

                    String username = "-";
                    String tableNumber = "-";

                    long userId = 0;
                    long tableId = 0;
                    long discountCouponId = 0;

                    if (order.getUser() != null) {

                        userId =
                                order.getUser().getUserId();

                        username =
                                order.getUser().getUsername();
                    }

                    if (order.getDiningTable() != null) {

                        tableId =
                                order.getDiningTable().getTableId();

                        tableNumber =
                                order.getDiningTable()
                                        .getTableNumber();
                    }

                    if (order.getDiscountCoupon() != null) {

                        discountCouponId =
                                order.getDiscountCoupon()
                                        .getCouponId();
                    }

                    return new OrderDTO(
                            order.getOrderId(),
                            order.getOrderDate(),
                            order.getTotalAmount(),
                            order.getStatus(),
                            userId,
                            tableId,
                            discountCouponId,
                            username,
                            tableNumber
                    );

                })
                .toList();
    }

    @Override
    public OrderDTO getOrderById(long orderId) {

        log.info("Execute method getOrderById");

        Optional<Order> optionalOrder =
                orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new RuntimeException(
                    "Sorry, related order is not found."
            );
        }

        Order order = optionalOrder.get();

        String username = "-";
        String tableNumber = "-";

        long userId = 0;
        long tableId = 0;
        long discountCouponId = 0;

        if (order.getUser() != null) {

            userId =
                    order.getUser().getUserId();

            username =
                    order.getUser().getUsername();
        }

        if (order.getDiningTable() != null) {

            tableId =
                    order.getDiningTable().getTableId();

            tableNumber =
                    order.getDiningTable()
                            .getTableNumber();
        }

        if (order.getDiscountCoupon() != null) {

            discountCouponId =
                    order.getDiscountCoupon()
                            .getCouponId();
        }

        return new OrderDTO(
                order.getOrderId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus(),
                userId,
                tableId,
                discountCouponId,
                username,
                tableNumber
        );
    }
}