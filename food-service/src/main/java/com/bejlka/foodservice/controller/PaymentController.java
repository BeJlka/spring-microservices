package com.bejlka.foodservice.controller;

import com.bejlka.foodservice.model.dto.PaymentDTO;
import com.bejlka.foodservice.security.SecurityUser;
import com.bejlka.foodservice.service.PaymentService;
import com.bejlka.foodservice.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    UserService userService;
    PaymentService paymentService;

    @GetMapping("/payments/{id}")
    public PaymentDTO payment(@PathVariable("id") Long id) {
        return paymentService.payment(id);
    }

    @GetMapping("/payments")
    public List<PaymentDTO> paymentAll(@AuthenticationPrincipal SecurityUser securityUser) {
        return paymentService.paymentsAll(userService.getUserByLogin(securityUser.getLogin()));
    }

    @PutMapping("/payments/{id}")
    public PaymentDTO paymentUpdate(@AuthenticationPrincipal SecurityUser securityUser, @PathVariable("id") Long id) {
        return paymentService.updatePayment(id);
    }
}
