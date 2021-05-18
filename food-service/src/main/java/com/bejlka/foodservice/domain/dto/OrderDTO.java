package com.bejlka.foodservice.domain.dto;

import com.bejlka.foodservice.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private Long id;
    private UserDTO user;
    private PaymentDTO payment;
    private DeliveryDTO delivery;
    private RestaurantDTO restaurant;
    private String address;
    private double amount;
    private Instant orderDate;
    private Instant deliveryDate;
    private Status status;
    private List<OrderItemDTO> items;
}
