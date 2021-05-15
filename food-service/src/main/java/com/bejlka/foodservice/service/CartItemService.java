package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.entity.Cart;
import com.bejlka.foodservice.domain.entity.CartItem;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.repository.CartItemRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemService {
    CartItemRepository cartItemRepository;

    public Optional<CartItem> findCartItem(Cart cart, MenuItem menuItem) {
        return cartItemRepository.findByUserIdAndMenuId(cart.getUserId(), menuItem.getId());
    }

    public void increment(CartItem cartItem) {
        cartItem.increment();
        cartItemRepository.save(cartItem);
    }

    public void decrement(CartItem cartItem) {
        cartItem.decrement();
        cartItemRepository.save(cartItem);
    }

    public CartItem create(Cart cart, MenuItem menuItem) {
        CartItem cartItem = new CartItem();
        cartItem.setUserId(cart.getUserId());
        cartItem.setMenuId(menuItem.getId());
        cartItem.setRestaurant(menuItem.getRestaurant());
        cartItem.setCount(1);
        cartItem.setPrice(menuItem.getPrice());
        cartItem.setName(menuItem.getName());
        return cartItemRepository.save(cartItem);
    }

    public void remove(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    public void removeAll(List<CartItem> items) {
        cartItemRepository.deleteAll(items);
    }
}
