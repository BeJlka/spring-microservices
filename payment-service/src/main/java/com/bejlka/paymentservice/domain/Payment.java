package com.bejlka.paymentservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    private Long orderId;
    private Long userId;
    private Instant paymentDate;
    @Enumerated(EnumType.STRING)
    private Status status;

}
