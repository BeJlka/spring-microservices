package com.bejlka.foodservice.model.dto;

import lombok.Data;

@Data
public class PaymentBodyDTO {
    private Long userId;
    private Long orderId;
}
