package com.bejlka.notificationservice.domain;

import lombok.Data;

@Data
public class NotificationDTO {
    private String email;
    private String name;
    private Long orderId;
    private double amount;
    private String status;
}
