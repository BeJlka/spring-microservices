package com.bejlka.foodservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cao_seq")
    @SequenceGenerator(sequenceName = "cao_seq", allocationSize = 1, name = "cao_seq")
    private Long id;
    private String name;
    private double price;
    @ManyToOne
    private Restaurant restaurant;
    private int count;

    public void increment() {
        this.count++;
    }

    public void decrement() {
        this.count--;
    }
}
