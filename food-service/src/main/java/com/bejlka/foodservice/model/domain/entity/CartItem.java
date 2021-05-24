package com.bejlka.foodservice.model.domain.entity;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cartitem_seq")
    @SequenceGenerator(sequenceName = "cartitem_seq", allocationSize = 1, name = "cartitem_seq")
    private Long id;

    private Long menuId;
    private String name;
    private Double price;

    @ManyToOne()
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "cart_id", insertable = false, updatable = false)
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "restaurant_id", insertable = false, updatable = false)
    private Long restaurantId;

    private Integer count;

    public void increment() {
        this.count++;
    }

    public void decrement() {
        this.count--;
    }
}

