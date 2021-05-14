package com.bejlka.foodservice.domain.entity;
import com.bejlka.foodservice.domain.enums.Role;
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
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_SEQ")
    @SequenceGenerator(sequenceName = "order_seq", allocationSize = 1, name = "ORDER_SEQ")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String address;

    private double amount;

//    private boolean payment;

    private Instant orderDate;
    private Instant deliveryDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany
    private List<OrderItem> items = new ArrayList<>();

}
