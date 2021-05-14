package com.bejlka.foodservice.repository;

import com.bejlka.foodservice.domain.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
