package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.dto.CartDTO;
import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.entity.Cart;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.domain.entity.Restaurant;
import com.bejlka.foodservice.domain.entity.User;
import com.bejlka.foodservice.domain.mapper.CartMapper;
import com.bejlka.foodservice.exeption.NotMatch;
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

    public Integer addItem(Cart cart, MenuItem menuItem) {
        if (cart.getItems().size() > 0) {
            Restaurant restaurant = cart.getItems().get(cart.getItems().size() - 1).getRestaurant();
            if (!restaurant.getId().equals(menuItem.getRestaurant().getId())) {
                throw new NotMatch("Блюда из разных ресторанов");
            }
        }
        cart.getItems().add(menuItem);
        cartRepository.save(cart);
        return cart.getItems().indexOf(menuItem);
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
