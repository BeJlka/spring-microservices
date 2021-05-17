package com.bejlka.foodservice.domain.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class PaymentDTO {
    private Long id;
    private Long userId;
    private Long orderId;
    private Instant paymentDate;
    private String status;
}
