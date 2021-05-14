package com.bejlka.foodservice.controller;

import com.bejlka.foodservice.domain.dto.CartDTO;
import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.entity.Cart;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.exeption.MenuItemNotFound;
import com.bejlka.foodservice.exeption.UserNotFound;
import com.bejlka.foodservice.security.SecurityUser;
import com.bejlka.foodservice.service.CartService;
import com.bejlka.foodservice.service.MenuItemService;
import com.bejlka.foodservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping()
    public CartDTO getCurrentCart(@AuthenticationPrincipal SecurityUser securityUser) {
        return cartService.getCart(userService.getUserByLogin(securityUser.getLogin()).getCart());
    }

    @GetMapping("/items")
    public List<MenuItemDTO> getAllMenuItems(@AuthenticationPrincipal SecurityUser securityUser) {
        return cartService.getAllMenuItems(userService.getUserByLogin(securityUser.getLogin()).getCart());
    }

    @PutMapping("/item/{id}")
    public Integer addItem(@AuthenticationPrincipal SecurityUser securityUser, @PathVariable("id") MenuItem menuItem) {
        return cartService.addItem(userService.getUserByLogin(securityUser.getLogin()).getCart(), menuItem);
    }

    @DeleteMapping("/item/{id}")
    public void removeItem(@AuthenticationPrincipal SecurityUser securityUser, @PathVariable("id") Long id) {
        cartService.removeItem(userService.getUserByLogin(securityUser.getLogin()).getCart(), id);
    }

    @DeleteMapping("/items")
    public void removeItems(@AuthenticationPrincipal SecurityUser securityUser) {
        cartService.removeItems(userService.getUserByLogin(securityUser.getLogin()).getCart());
    }
}
