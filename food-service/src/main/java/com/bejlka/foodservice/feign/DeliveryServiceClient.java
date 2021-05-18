package com.bejlka.foodservice.feign;

import com.bejlka.foodservice.domain.dto.DeliveryDTO;
import com.bejlka.foodservice.domain.dto.DeliveryBodyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "delivery-service")
public interface DeliveryServiceClient {

    @GetMapping("/deliveries/{id}")
    DeliveryDTO delivery(@PathVariable("id") Long id);

    @GetMapping("/deliveries/user/{id}")
    List<DeliveryDTO> deliveryAll(@PathVariable Long id);

    @PostMapping("/deliveries/")
    DeliveryDTO createDelivery(@RequestBody DeliveryBodyDTO deliveryBodyDTO);

    @PutMapping("/deliveries/{id}")
    DeliveryDTO updateStatusDelivery(@PathVariable("id") Long id);
}
