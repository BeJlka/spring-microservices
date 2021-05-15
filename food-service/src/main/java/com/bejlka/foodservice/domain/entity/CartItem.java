package com.bejlka.foodservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(CartItemId.class)
public class CartItem {
    @Id
    private Long userId;
    @Id
    private Long menuId;
    private String name;
    private Double price;
    @ManyToOne
    private Restaurant restaurant;
    private Integer count;

    public void increment() {
        this.count++;
    }

    public void decrement() {
        this.count--;
    }
}

