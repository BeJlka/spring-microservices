package com.bejlka.foodservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESTAURANT_SEQ")
    @SequenceGenerator(sequenceName = "restaurant_seq", allocationSize = 1, name = "RESTAURANT_SEQ")
    private Long id;

    @Column(unique = true)
    private String name;

    private String address;

    @OneToMany
    private List<MenuItem> items = new ArrayList<>();
}
