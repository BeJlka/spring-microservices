package com.bejlka.notificationservice.domain;

import lombok.Data;

@Data
public class NotificationDTO {
    private String email;
    private Long orderId;
    private String title;
    private String massage;
}
