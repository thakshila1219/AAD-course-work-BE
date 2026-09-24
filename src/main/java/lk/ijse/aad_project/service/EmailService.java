package lk.ijse.aad_project.service;

import lk.ijse.aad_project.entity.Order;

public interface EmailService {

    void sendOrderConfirmationEmail(Order order);
}