package com.bejlka.foodservice.domain.dto;

import lombok.Data;

@Data
public class PaymentBodyDTO {
    private Long userId;
    private Long orderId;
}
