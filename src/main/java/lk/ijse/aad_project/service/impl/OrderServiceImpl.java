package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.OrderDTO;
import lk.ijse.aad_project.entity.DiscountCoupon;
import lk.ijse.aad_project.entity.DiningTable;
import lk.ijse.aad_project.entity.Ingredient;
import lk.ijse.aad_project.entity.Order;
import lk.ijse.aad_project.entity.OrderDetail;
import lk.ijse.aad_project.entity.RecipeItem;
import lk.ijse.aad_project.entity.User;
import lk.ijse.aad_project.repository.*;
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

    private final OrderDetailRepository orderDetailRepository;
    private final RecipeItemRepository recipeItemRepository;
    private final IngredientRepository ingredientRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            UserRepository userRepository,
            DiningTableRepository diningTableRepository,
            DiscountCouponRepository discountCouponRepository,
            EmailService emailService,
            OrderDetailRepository orderDetailRepository,
            RecipeItemRepository recipeItemRepository,
            IngredientRepository ingredientRepository
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.diningTableRepository = diningTableRepository;
        this.discountCouponRepository = discountCouponRepository;
        this.emailService = emailService;
        this.orderDetailRepository = orderDetailRepository;
        this.recipeItemRepository = recipeItemRepository;
        this.ingredientRepository = ingredientRepository;
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

            /*
             * Stock is deducted only when the order
             * changes to CONFIRMED for the first time.
             */
            if (!"CONFIRMED".equalsIgnoreCase(oldStatus)
                    && "CONFIRMED".equalsIgnoreCase(newStatus)) {

                log.info(
                        "Order {} is being confirmed. Checking ingredient stock...",
                        order.getOrderId()
                );

                deductIngredientStock(order);

                log.info(
                        "Ingredient stock successfully deducted for Order {}",
                        order.getOrderId()
                );
            }

            order.setStatus(newStatus);

            orderRepository.save(order);

            log.info(
                    "STATUS UPDATE -> Order ID: {}, Old Status: {}, New Status: {}",
                    order.getOrderId(),
                    oldStatus,
                    newStatus
            );

            /*
             * Send confirmation email.
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

    /**
     * Deduct ingredients according to the recipes
     * of all menu items in the order.
     */
    private void deductIngredientStock(Order order) {

        log.info(
                "Starting stock deduction for Order ID: {}",
                order.getOrderId()
        );

        List<OrderDetail> orderDetails =
                orderDetailRepository.findByOrderOrderId(
                        order.getOrderId()
                );

        if (orderDetails == null || orderDetails.isEmpty()) {

            log.info(
                    "No order details found for Order ID: {}",
                    order.getOrderId()
            );

            return;
        }

        /*
         * First check whether enough stock exists
         * for every ingredient.
         *
         * We do this BEFORE changing any stock.
         */
        for (OrderDetail orderDetail : orderDetails) {

            if (orderDetail.getMenuItem() == null) {
                throw new RuntimeException(
                        "Menu item is missing for Order Detail ID: "
                                + orderDetail.getOrderDetailId()
                );
            }

            long menuItemId =
                    orderDetail.getMenuItem().getItemId();

            int orderedQuantity =
                    orderDetail.getQuantity();

            if (orderedQuantity <= 0) {
                throw new RuntimeException(
                        "Invalid order quantity for Menu Item ID: "
                                + menuItemId
                );
            }

            List<RecipeItem> recipeItems =
                    recipeItemRepository.findAll()
                            .stream()
                            .filter(recipeItem ->
                                    recipeItem.getMenuItem() != null
                                            && recipeItem.getMenuItem()
                                            .getItemId() == menuItemId
                            )
                            .toList();

            if (recipeItems.isEmpty()) {

                log.warn(
                        "No recipe found for Menu Item ID: {}",
                        menuItemId
                );

                continue;
            }

            for (RecipeItem recipeItem : recipeItems) {

                Ingredient ingredient =
                        recipeItem.getIngredient();

                if (ingredient == null) {
                    throw new RuntimeException(
                            "Ingredient is missing for Recipe Item ID: "
                                    + recipeItem.getRecipeItemId()
                    );
                }

                double requiredQuantity =
                        recipeItem.getRequiredQuantity()
                                * orderedQuantity;

                if (ingredient.getQuantityOnHand()
                        < requiredQuantity) {

                    throw new RuntimeException(
                            "Insufficient stock for ingredient: "
                                    + ingredient.getName()
                                    + ". Required: "
                                    + requiredQuantity
                                    + " "
                                    + ingredient.getUnit()
                                    + ", Available: "
                                    + ingredient.getQuantityOnHand()
                                    + " "
                                    + ingredient.getUnit()
                    );
                }
            }
        }

        /*
         * Stock is sufficient for every ingredient.
         * Now deduct the quantities.
         */
        for (OrderDetail orderDetail : orderDetails) {

            long menuItemId =
                    orderDetail.getMenuItem().getItemId();

            int orderedQuantity =
                    orderDetail.getQuantity();

            List<RecipeItem> recipeItems =
                    recipeItemRepository.findAll()
                            .stream()
                            .filter(recipeItem ->
                                    recipeItem.getMenuItem() != null
                                            && recipeItem.getMenuItem()
                                            .getItemId() == menuItemId
                            )
                            .toList();

            for (RecipeItem recipeItem : recipeItems) {

                Ingredient ingredient =
                        recipeItem.getIngredient();

                double requiredQuantity =
                        recipeItem.getRequiredQuantity()
                                * orderedQuantity;

                double oldStock =
                        ingredient.getQuantityOnHand();

                double newStock =
                        oldStock - requiredQuantity;

                ingredient.setQuantityOnHand(newStock);

                ingredientRepository.save(ingredient);

                log.info(
                        "STOCK UPDATE -> Ingredient: {}, Old: {}, Used: {}, New: {}",
                        ingredient.getName(),
                        oldStock,
                        requiredQuantity,
                        newStock
                );
            }
        }

        log.info(
                "Stock deduction completed for Order ID: {}",
                order.getOrderId()
        );
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