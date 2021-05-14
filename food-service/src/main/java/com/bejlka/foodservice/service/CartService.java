package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.dto.CartDTO;
import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.entity.Cart;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.domain.entity.User;
import com.bejlka.foodservice.domain.mapper.CartMapper;
import com.bejlka.foodservice.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public Cart createCart() {
        return cartRepository.save(new Cart());
    }

    public CartDTO getCart(Cart cart) {
        return cartMapper.map(cart);
    }

    public List<MenuItemDTO> getAllMenuItems(Cart cart) {
        return cartMapper.map(cart).getItems();
    }

    public Integer addItem(MenuItem menuItem, User user) {
        user.getCart().getItems().add(menuItem);
        cartRepository.save(user.getCart());
        return user.getCart().getItems().indexOf(menuItem);
    }

    public void removeItem(Cart cart, MenuItem menuItem) {
        cart.getItems().remove(menuItem);
        cartRepository.save(cart);
    }

    public void removeItems(Cart cart) {
        cart.setItems(new ArrayList<>());
        cartRepository.save(cart);

    }
}
