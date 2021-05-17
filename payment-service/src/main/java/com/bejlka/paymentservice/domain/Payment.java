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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(sequenceName = "payment_seq", allocationSize = 1, name = "payment_seq")
    private Long id;
    private Long userId;
    private Long orderId;
    private Instant paymentDate;
    @Enumerated(EnumType.STRING)
    private Status status;

}
