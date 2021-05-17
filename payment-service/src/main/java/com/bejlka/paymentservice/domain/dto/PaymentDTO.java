package com.bejlka.paymentservice.domain.dto;

import com.bejlka.paymentservice.domain.Status;
import lombok.Data;

import java.time.Instant;

@Data
public class PaymentDTO {
    private Long id;
    private Long userId;
    private Long orderId;
    private Instant paymentDate;
    private Status status;
}
