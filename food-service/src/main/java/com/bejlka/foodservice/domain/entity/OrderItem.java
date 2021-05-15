package com.bejlka.foodservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderitem_seq")
    @SequenceGenerator(sequenceName = "orderitem_seq", allocationSize = 1, name = "orderitem_seq")
    private Long id;
    private String name;
    private Double price;
    @ManyToOne
    private Restaurant restaurant;
    private Integer count = 1;

    public void increment() {
        this.count++;
    }

    public void decrement() {
        this.count--;
    }
}
