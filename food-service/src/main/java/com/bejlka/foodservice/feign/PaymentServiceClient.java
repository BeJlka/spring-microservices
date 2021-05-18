package com.bejlka.foodservice.feign;

import com.bejlka.foodservice.domain.dto.PaymentDTO;
import com.bejlka.foodservice.domain.dto.PaymentBodyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
