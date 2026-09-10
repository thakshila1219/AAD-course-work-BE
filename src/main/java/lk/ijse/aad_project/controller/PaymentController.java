package lk.ijse.aad_project.controller;

import lk.ijse.aad_project.dto.PaymentDTO;
import lk.ijse.aad_project.service.PaymentService; 
import lk.ijse.aad_project.contant.CommonResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static lk.ijse.aad_project.contant.ResponseMessage.SUCCESS_MESSAGE;
import static lk.ijse.aad_project.contant.ResponseStatusCode.OPERATION_SUCCESS;

@RestController
@RequestMapping(value = "v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse savePayment(@RequestBody PaymentDTO paymentDTO){
        paymentService.savePayment(paymentDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse updatePayment(@RequestBody PaymentDTO paymentDTO){
        paymentService.updatePayment(paymentDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}
