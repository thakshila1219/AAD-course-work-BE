package lk.ijse.aad_project.service;

import lk.ijse.aad_project.dto.PaymentDTO;

public interface PaymentService {
    void savePayment(PaymentDTO paymentDTO);
    void updatePayment(PaymentDTO paymentDTO);
    void removePayment(long paymentId); 
}
