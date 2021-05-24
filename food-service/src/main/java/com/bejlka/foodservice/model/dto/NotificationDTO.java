package com.bejlka.foodservice.model.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private String email;
    private Long orderId;
    private String title;
    private String message;
}
