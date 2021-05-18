package com.bejlka.deliveryservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRequestDTO {
    private Long orderId;
    private Long userId;
    private String restaurantAddress;
    private String deliveryAddress;
    private Instant orderDate;
}
