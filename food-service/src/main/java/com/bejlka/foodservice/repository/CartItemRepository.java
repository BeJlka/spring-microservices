package com.bejlka.foodservice.repository;

import com.bejlka.foodservice.model.domain.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

//    Optional<CartItem> findByUserIdAndMenuId(Long userId, Long menuId);

    Optional<CartItem> findByMenuIdAndAndCartId(Long CartId, Long menuId);
}
