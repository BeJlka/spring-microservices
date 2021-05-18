package com.bejlka.paymentservice.domain.dto;

import com.bejlka.paymentservice.domain.Status;
import lombok.Data;

import java.time.Instant;

@Data
public class PaymentDTO {
    private Long orderId;
    private Long userId;
    private Instant paymentDate;
    private Status status;
}
