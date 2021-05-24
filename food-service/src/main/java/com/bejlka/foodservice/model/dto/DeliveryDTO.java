package com.bejlka.foodservice.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryDTO {
    private Long orderId;
    private Long userId;
    private String restaurantAddress;
    private String deliveryAddress;
    private Instant orderDate;
    private Instant deliveryDate;
    private String status;
}
