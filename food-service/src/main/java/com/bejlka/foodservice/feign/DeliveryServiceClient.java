package com.bejlka.foodservice.feign;

import com.bejlka.foodservice.model.dto.DeliveryBodyDTO;
import com.bejlka.foodservice.model.dto.DeliveryDTO;
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

    @PutMapping("/deliveries/cancel/{id}")
    DeliveryDTO cancelDelivery(@PathVariable("id") Long id);
}
