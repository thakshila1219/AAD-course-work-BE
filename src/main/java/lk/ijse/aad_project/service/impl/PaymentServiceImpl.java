package lk.ijse.aad_project.service.impl;

import lk.ijse.aad_project.dto.PaymentDTO;
import lk.ijse.aad_project.entity.Order;
import lk.ijse.aad_project.entity.Payment;
import lk.ijse.aad_project.repository.OrderRepository;
import lk.ijse.aad_project.repository.PaymentRepository;
import lk.ijse.aad_project.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void savePayment(PaymentDTO paymentDTO) {
        log.info("Execute method savePayment");
        try {
            Payment payment = new Payment();
            payment.setAmount(paymentDTO.getAmount());
            payment.setPaymentDate(paymentDTO.getPaymentDate());
            payment.setPaymentMethod(paymentDTO.getPaymentMethod());

            Optional<Order> optionalOrder = orderRepository.findById(paymentDTO.getOrderId());
            if (optionalOrder.isEmpty())
                throw new RuntimeException("Sorry, related order is not found.");

            payment.setOrder(optionalOrder.get());
            paymentRepository.save(payment);
        } catch (Exception e) {
            log.error("Error in savePayment : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void updatePayment(PaymentDTO paymentDTO) {
        log.info("Execute method updatePayment");
        try {
            Optional<Payment> optionalPayment = paymentRepository.findById(paymentDTO.getPaymentId());
            if (optionalPayment.isEmpty())
                throw new RuntimeException("Sorry, related payment is not found.");

            Payment payment = optionalPayment.get();
            payment.setAmount(paymentDTO.getAmount());
            payment.setPaymentDate(paymentDTO.getPaymentDate());
            payment.setPaymentMethod(paymentDTO.getPaymentMethod());

            Optional<Order> optionalOrder = orderRepository.findById(paymentDTO.getOrderId());
            if (optionalOrder.isEmpty())
                throw new RuntimeException("Sorry, related order is not found.");

            payment.setOrder(optionalOrder.get());
            paymentRepository.save(payment);
        } catch (Exception e) {
            log.error("Error in updatePayment : " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void removePayment(long paymentId) {
        log.info("Execute method removePayment");
        try {
            Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
            if (optionalPayment.isEmpty())
                throw new RuntimeException("Sorry, related payment is not found.");

            paymentRepository.deleteById(paymentId);
        } catch (Exception e) {
            log.error("Error in removePayment : " + e.getMessage());
            throw e;
        }
    }
}