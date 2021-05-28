package com.bejlka.paymentservice.controller;

import com.bejlka.paymentservice.domain.dto.PaymentDTO;
import com.bejlka.paymentservice.domain.dto.PaymentRequestDTO;
import com.bejlka.paymentservice.service.PaymentService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;

    @GetMapping("/user/{id}")
    public List<PaymentDTO> getPayments(@PathVariable("id") Long id) {
        return paymentService.paymentsAll(id);
    }

    @GetMapping("/{id}")
    public PaymentDTO getPayment(@PathVariable("id") Long id) {
        return paymentService.payment(id);
    }

    @PostMapping("/")
    public PaymentDTO createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        return paymentService.createPayment(paymentRequestDTO);
    }

    @PutMapping("/{id}")
    public PaymentDTO updatePayment(@PathVariable("id") Long id) {
        return paymentService.updatePayment(id);
    }

    @PutMapping("/cancel/{id}")
    public PaymentDTO cancelPayment(@PathVariable("id") Long id) {
        return paymentService.cancelPayment(id);
    }
}
