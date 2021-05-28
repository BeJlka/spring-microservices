package com.bejlka.foodservice.controller;

import com.bejlka.foodservice.model.dto.DeliveryDTO;
import com.bejlka.foodservice.security.SecurityUser;
import com.bejlka.foodservice.service.DeliveryService;
import com.bejlka.foodservice.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeliveryController {
    DeliveryService deliveryService;
    UserService userService;

    @GetMapping("/{id}")
    public DeliveryDTO payment(@PathVariable("id") Long id) {
        return deliveryService.delivery(id);
    }

    @GetMapping()
    public List<DeliveryDTO> paymentAll(@AuthenticationPrincipal SecurityUser securityUser) {
        return deliveryService.deliveryAll(userService.getUserByLogin(securityUser.getLogin()));
    }

//    @PutMapping("/{id}")
//    public DeliveryDTO paymentUpdate(@AuthenticationPrincipal SecurityUser securityUser, @PathVariable("id") Long id) {
//        return deliveryService.updateStatusDelivery(userService.getUserByLogin(securityUser.getLogin()), id);
//    }
}
