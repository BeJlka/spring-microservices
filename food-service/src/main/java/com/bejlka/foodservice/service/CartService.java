package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.dto.CartDTO;
import com.bejlka.foodservice.domain.dto.CartItemDTO;
import com.bejlka.foodservice.domain.entity.Cart;
import com.bejlka.foodservice.domain.entity.CartItem;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.domain.entity.User;
import com.bejlka.foodservice.domain.mapper.CartMapper;
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

    public Cart createCart(Long id) {
        Cart cart = new Cart();
        cart.setUserId(id);
        cart.setItems(new ArrayList<>());
        return cartRepository.save(cart);
    }

    public CartDTO getCart(Cart cart) {
        return cartMapper.cartToDTO(cart);
    }

    public List<CartItemDTO> getAllMenuItems(Cart cart) {
        return cartMapper.cartToDTO(cart).getItems();
    }

    public CartDTO addItem(User user, MenuItem menuItem) {
        Cart cart = user.getCart();
        if (cart == null) {
            cart = createCart(user.getId());
        }
        Optional<CartItem> optionalCartItem = cartItemService.findCartItem(cart, menuItem);
        if (optionalCartItem.isPresent()) {
            cartItemService.increment(optionalCartItem.get());
        } else {
            if (cart.getItems().isEmpty() || cart.getItems().get(0).getRestaurant().equals(menuItem.getRestaurant())) {
                cart.getItems().add(cartItemService.create(cart, menuItem));
                cartRepository.save(cart);
            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Очистите корзину прежде чем добавлять данный товар в нее");
            }
        }
        return cartMapper.cartToDTO(cart);
    }

    public void remove(User user, MenuItem menuItem) {
        Cart cart = user.getCart();
        if (cart == null || cart.getItems().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Корзина пустая");
        }
        Optional<CartItem> optionalCartItem = cartItemService.findCartItem(cart, menuItem);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            if (cartItem.getCount() > 1) {
                cartItemService.decrement(cartItem);
            } else {
                cart.getItems().remove(cartItem);
                cartItemService.remove(cartItem);
            }
        }
        cartRepository.save(cart);
    }

    public void removeAll(Cart cart) {
        if (cart.getItems().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Корзина пустая");
        }
        List<CartItem> items = cart.getItems();
        cart.setItems(new ArrayList<>());
        cartItemService.removeAll(items);
        cartRepository.save(cart);
    }
}
