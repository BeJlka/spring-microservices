package com.bejlka.foodservice.service;

import com.bejlka.foodservice.model.dto.CartDTO;
import com.bejlka.foodservice.model.domain.entity.Cart;
import com.bejlka.foodservice.model.domain.entity.CartItem;
import com.bejlka.foodservice.model.domain.entity.MenuItem;
import com.bejlka.foodservice.model.domain.entity.User;
import com.bejlka.foodservice.model.mapper.CartMapper;
import com.bejlka.foodservice.exeption.CustomException;
import com.bejlka.foodservice.repository.CartRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {

    CartRepository cartRepository;
    CartItemService cartItemService;
    CartMapper cartMapper;

    public CartDTO getCart(Cart cart) {
        return cartMapper.cartToDTO(cart);
    }

    public CartDTO addItem(User user, MenuItem menuItem) {
        Cart cart = user.getCart();
        Optional<CartItem> optionalCartItem = cartItemService.findCartItem(cart, menuItem);
        if (optionalCartItem.isPresent()) {
            optionalCartItem.get().increment();
        } else {
            if (cart.getItems().isEmpty() || cart.getItems().get(0).getRestaurant().equals(menuItem.getRestaurant())) {
                cart.getItems().add(cartItemService.create(cart, menuItem));
            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Очистите корзину прежде чем добавлять данный товар в нее");
            }
        }
        cartRepository.save(cart);
        return cartMapper.cartToDTO(cart);
    }

    public void remove(User user,CartItem cartItem) {
            if (cartItem.getCount() > 1) {
                cartItem.decrement();
            } else {
                user.getCart().getItems().remove(cartItem);
            }
//        }
        cartRepository.save(user.getCart());
    }

    public void removeAll(Cart cart) {
        if (cart.getItems().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Корзина пустая");
        }
        cart.getItems().removeAll(cart.getItems());
        cartRepository.save(cart);
    }
}
