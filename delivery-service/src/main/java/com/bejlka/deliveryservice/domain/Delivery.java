package com.bejlka.deliveryservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
    @Id
    private Long orderId;
    private Long userId;
    private String restaurantAddress;
    private String deliveryAddress;
    private Instant orderDate;
    private Instant deliveryDate;
    @Enumerated(EnumType.STRING)
    private Status status;
}
