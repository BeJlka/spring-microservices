package com.bejlka.foodservice.feign;

import com.bejlka.foodservice.model.dto.PaymentBodyDTO;
import com.bejlka.foodservice.model.dto.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {

    @GetMapping(value = "/payments/user/{id}")
    List<PaymentDTO> getPayments(@PathVariable("id") Long id);

    @GetMapping(value = "/payments/{id}")
    PaymentDTO getPayment(@PathVariable("id") Long id);

    @PostMapping("/payments/")
    PaymentDTO createPayment(PaymentBodyDTO paymentBodyDTO);

    @PutMapping("/payments/{id}")
    PaymentDTO updatePayment(@PathVariable("id") Long id);
}
