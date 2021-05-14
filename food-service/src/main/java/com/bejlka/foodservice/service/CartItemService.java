package com.bejlka.foodservice.service;

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

    public CartItem create(MenuItem menuItem) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(menuItem.getId());
        if (optionalCartItem.isPresent()) {
            optionalCartItem.get().increment();
            return cartItemRepository.save(optionalCartItem.get());
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setRestaurant(menuItem.getRestaurant());
            cartItem.setCount(1);
            cartItem.setPrice(menuItem.getPrice());
            cartItem.setName(menuItem.getName());
            return cartItemRepository.save(cartItem);
        }
    }

    public void remove(MenuItem menuItem) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(menuItem.getId());
        if(optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            if (cartItem.getCount() > 1) {
                cartItem.decrement();
                cartItemRepository.save(cartItem);
            } else {
                cartItemRepository.delete(cartItem);
            }
        }
    }
}
