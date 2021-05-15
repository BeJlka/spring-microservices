package com.bejlka.foodservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENU_SEQ")
    @SequenceGenerator(sequenceName = "menu_seq", allocationSize = 1, name = "MENU_SEQ")
    private Long id;
    private String name;
    private Double price;

    @ManyToOne
    private Restaurant restaurant;
}
