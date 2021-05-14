package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.dto.CartDTO;
import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.entity.*;
import com.bejlka.foodservice.domain.mapper.CartMapper;
import com.bejlka.foodservice.exeption.NotMatch;
import com.bejlka.foodservice.repository.CartRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {

    CartRepository cartRepository;
    CartItemService cartItemService;
    MenuItemService menuItemService;
    CartMapper cartMapper;

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
//        cart.getItems().add(menuItem);
        CartItem cartItem = cartItemService.create(menuItem);
        cart.getItems().add(cartItem);
        cartRepository.save(cart);
        return cart.getItems().indexOf(cartItem);
    }

    public void removeItem(Cart cart, Long id) {
        cartItemService.remove(menuItemService.findMenuById(id));
//        if (cart.getItems().size() < id) {
//            throw new NotMatch("Id выходит за пределы корзины");
//        }
//        cart.getItems().remove(id-1);
//        cartRepository.save(cart);
    }

    public void removeItems(Cart cart) {
        cart.setItems(new ArrayList<>());
        cartRepository.save(cart);
    }
}
