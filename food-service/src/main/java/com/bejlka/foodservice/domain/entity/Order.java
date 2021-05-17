package com.bejlka.foodservice.domain.entity;
import com.bejlka.foodservice.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(sequenceName = "order_seq", allocationSize = 1, name = "order_seq")
    private Long id;

    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String address;

    private Double amount;

    private Instant orderDate;
    private Instant deliveryDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany
    private List<OrderItem> items = new ArrayList<>();

}
