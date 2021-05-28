package com.bejlka.foodservice.service;

import com.bejlka.foodservice.model.domain.entity.Cart;
import com.bejlka.foodservice.model.domain.entity.CartItem;
import com.bejlka.foodservice.model.domain.entity.MenuItem;
import com.bejlka.foodservice.repository.CartItemRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemService {
    CartItemRepository cartItemRepository;

    public Optional<CartItem> findCartItem(Cart cart, MenuItem menuItem) {
        return cartItemRepository.findByMenuIdAndAndCartId(cart.getId(), menuItem.getId());
    }

    public Optional<CartItem> findCartItem(CartItem cartItem) {
        return cartItemRepository.findById(cartItem.getId());
    }

    public CartItem create(Cart cart, MenuItem menuItem) {
        CartItem cartItem = new CartItem();
        cartItem.setMenuId(menuItem.getId());
        cartItem.setRestaurant(menuItem.getRestaurant());
        cartItem.setCount(1);
        cartItem.setCart(cart);
        cartItem.setPrice(menuItem.getPrice());
        cartItem.setName(menuItem.getName());
        return cartItem;
    }
}
