package lk.ijse.aad_project.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lk.ijse.aad_project.entity.Order;
import lk.ijse.aad_project.entity.OrderDetail;

import lk.ijse.aad_project.service.EmailService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendOrderConfirmationEmail(Order order) {

        if (order == null) {
            log.warn("Cannot send email. Order is null.");
            return;
        }

        if (order.getUser() == null) {
            log.warn(
                    "Cannot send order confirmation email. User is null for order ID: {}",
                    order.getOrderId()
            );
            return;
        }

        String customerEmail = order.getUser().getEmail();

        if (customerEmail == null || customerEmail.isBlank()) {
            log.warn(
                    "Cannot send order confirmation email. Customer email is empty for order ID: {}",
                    order.getOrderId()
            );
            return;
        }

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(customerEmail);

            helper.setSubject(
                    "Order #" + order.getOrderId() + " Confirmed - RESTManager"
            );

            String htmlContent = buildOrderConfirmationEmail(order);

            helper.setText(htmlContent, true);

            mailSender.send(message);

            log.info(
                    "Order confirmation email sent successfully to {} for order ID: {}",
                    customerEmail,
                    order.getOrderId()
            );

        } catch (MessagingException e) {

            log.error(
                    "Failed to send order confirmation email for order ID: {}. Error: {}",
                    order.getOrderId(),
                    e.getMessage()
            );

            throw new RuntimeException(
                    "Failed to send order confirmation email.",
                    e
            );
        }
    }

    private String buildOrderConfirmationEmail(Order order) {

        String customerName =
                order.getUser().getUsername() != null
                        ? order.getUser().getUsername()
                        : "Customer";

        String orderDate = "-";

        if (order.getOrderDate() != null) {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            orderDate =
                    order.getOrderDate().format(formatter);
        }

        String tableNumber = "-";

        if (order.getDiningTable() != null) {
            tableNumber =
                    order.getDiningTable().getTableNumber();
        }

        StringBuilder orderItems = new StringBuilder();

        orderItems.append("""
                <table style="width:100%; border-collapse:collapse; margin-top:20px;">
                    <thead>
                        <tr>
                            <th style="border:1px solid #ddd; padding:10px; text-align:left;">
                                Item
                            </th>
                            <th style="border:1px solid #ddd; padding:10px; text-align:center;">
                                Quantity
                            </th>
                            <th style="border:1px solid #ddd; padding:10px; text-align:right;">
                                Unit Price
                            </th>
                            <th style="border:1px solid #ddd; padding:10px; text-align:right;">
                                Total
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                """);

        if (order.getOrderDetailList() != null
                && !order.getOrderDetailList().isEmpty()) {

            for (OrderDetail detail : order.getOrderDetailList()) {

                String itemName = "Unknown Item";

                if (detail.getMenuItem() != null
                        && detail.getMenuItem().getName() != null) {

                    itemName =
                            detail.getMenuItem().getName();
                }

                double itemTotal =
                        detail.getQuantity()
                                * detail.getUnitPrice();

                orderItems.append("""
                        <tr>
                            <td style="border:1px solid #ddd; padding:10px;">
                                """)
                        .append(itemName)
                        .append("""
                            </td>
                            <td style="border:1px solid #ddd; padding:10px; text-align:center;">
                                """)
                        .append(detail.getQuantity())
                        .append("""
                            </td>
                            <td style="border:1px solid #ddd; padding:10px; text-align:right;">
                                Rs. """)
                        .append(String.format("%.2f", detail.getUnitPrice()))
                        .append("""
                            </td>
                            <td style="border:1px solid #ddd; padding:10px; text-align:right;">
                                Rs. """)
                        .append(String.format("%.2f", itemTotal))
                        .append("""
                            </td>
                        </tr>
                        """);
            }

        } else {

            orderItems.append("""
                    <tr>
                        <td colspan="4"
                            style="border:1px solid #ddd; padding:10px; text-align:center;">
                            No order details available
                        </td>
                    </tr>
                    """);
        }

        orderItems.append("""
                </tbody>
                </table>
                """);

        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Order Confirmation</title>
                </head>

                <body style="
                    margin:0;
                    padding:0;
                    background-color:#f4f4f4;
                    font-family:Arial, sans-serif;
                ">

                    <div style="
                        max-width:700px;
                        margin:30px auto;
                        background:white;
                        padding:30px;
                        border-radius:10px;
                        box-shadow:0 2px 10px rgba(0,0,0,0.1);
                    ">

                        <h1 style="
                            color:#2e7d32;
                            text-align:center;
                        ">
                            Order Confirmed
                        </h1>

                        <p>
                            Hello <strong>"""
                + customerName
                + """
                            </strong>,
                        </p>

                        <p>
                            Your order has been successfully confirmed.
                            Thank you for choosing RESTManager.
                        </p>

                        <div style="
                            background:#f8f8f8;
                            padding:15px;
                            border-radius:8px;
                            margin-top:20px;
                        ">

                            <p>
                                <strong>Order ID:</strong>
                                #"""
                + order.getOrderId()
                + """
                            </p>

                            <p>
                                <strong>Order Date:</strong>
                                """
                + orderDate
                + """
                            </p>

                            <p>
                                <strong>Table:</strong>
                                """
                + tableNumber
                + """
                            </p>

                            <p>
                                <strong>Status:</strong>
                                <span style="
                                    color:#2e7d32;
                                    font-weight:bold;
                                ">
                                    CONFIRMED
                                </span>
                            </p>

                        </div>

                        <h3 style="margin-top:25px;">
                            Order Details
                        </h3>

                        """
                + orderItems
                + """

                        <div style="
                            text-align:right;
                            margin-top:20px;
                            font-size:18px;
                        ">

                            <strong>
                                Total Amount:
                                Rs. """
                + String.format("%.2f", order.getTotalAmount())
                + """
                            </strong>

                        </div>

                        <p style="
                            margin-top:30px;
                            text-align:center;
                            color:#666;
                        ">
                            Thank you for ordering with RESTManager.
                        </p>

                    </div>

                </body>
                </html>
                """;
    }
}