package com.bejlka.foodservice.domain.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private String email;
    private Long orderId;
    private String title;
    private String massage;
}
