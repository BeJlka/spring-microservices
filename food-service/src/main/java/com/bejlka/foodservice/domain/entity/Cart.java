package com.bejlka.foodservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CART_SEQ")
    @SequenceGenerator(sequenceName = "cart_seq", allocationSize = 1, name = "CART_SEQ")
    private Long id;

    @OneToOne(mappedBy = "cart")
    private User user;

    @OneToMany
    private List<MenuItem> items = new ArrayList<>();
}
