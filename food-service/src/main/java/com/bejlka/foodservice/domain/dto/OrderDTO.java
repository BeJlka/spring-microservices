package com.bejlka.foodservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private UserDTO userDTO;
    private RestaurantDTO restaurantDTO;
    private String address;
    private double amount;
    private Instant orderDate;
    private Instant deliveryDate;
    private List<MenuItemDTO> items;
}
