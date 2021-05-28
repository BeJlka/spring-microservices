package com.bejlka.foodservice.model.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private String email;
    private String name;
    private Long orderId;
    private double amount;
    private String status;
}
