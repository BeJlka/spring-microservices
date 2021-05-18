package com.bejlka.deliveryservice.controller;

import com.bejlka.deliveryservice.domain.dto.DeliveryDTO;
import com.bejlka.deliveryservice.domain.dto.DeliveryRequestDTO;
import com.bejlka.deliveryservice.service.DeliveryService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeliveryController {
    DeliveryService deliveryService;

    @GetMapping("/{id}")
    public DeliveryDTO delivery(@PathVariable("id") Long id) {
        return deliveryService.delivery(id);
    }

    @GetMapping("/user/{id}")
    public List<DeliveryDTO> deliveryAll(@PathVariable Long id) {
        return deliveryService.deliveryAll(id);
    }

    @PostMapping("/")
    public DeliveryDTO createDelivery(@RequestBody DeliveryRequestDTO deliveryRequestDTO) {
        return deliveryService.createDelivery(deliveryRequestDTO);
    }

    @PutMapping("/{id}")
    public DeliveryDTO updateDelivery(@PathVariable("id") Long id) {
        return deliveryService.updateStatusDelivery(id);
    }
}
