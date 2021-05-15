package com.bejlka.foodservice.controller;

import com.bejlka.foodservice.domain.dto.CartDTO;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.security.SecurityUser;
import com.bejlka.foodservice.service.CartService;
import com.bejlka.foodservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
//
//    @GetMapping("/items")
//    public List<MenuItemDTO> getAllMenuItems(@AuthenticationPrincipal SecurityUser securityUser) {
//        return cartService.getAllMenuItems(userService.getUserByLogin(securityUser.getLogin()).getCart());
//    }

    @PutMapping("/items/{id}")
    public CartDTO addItem(@AuthenticationPrincipal SecurityUser securityUser, @PathVariable("id") MenuItem menuItem) {
        return cartService.addItem(userService.getUserByLogin(securityUser.getLogin()), menuItem);
    }

    @DeleteMapping("/items/{id}")
    public void removeItem(@AuthenticationPrincipal SecurityUser securityUser,  @PathVariable("id") MenuItem menuItem) {
        cartService.remove(userService.getUserByLogin(securityUser.getLogin()), menuItem);
    }

    @DeleteMapping("/items")
    public void removeItems(@AuthenticationPrincipal SecurityUser securityUser) {
        cartService.removeAll(userService.getUserByLogin(securityUser.getLogin()).getCart());
    }
}
