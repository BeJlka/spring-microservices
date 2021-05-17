package com.bejlka.paymentservice.domain.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long userId;
    private Long orderId;
}
