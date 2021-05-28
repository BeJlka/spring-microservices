package com.bejlka.foodservice.controller;

import com.bejlka.foodservice.model.domain.entity.CartItem;
import com.bejlka.foodservice.model.domain.entity.MenuItem;
import com.bejlka.foodservice.model.dto.CartDTO;
import com.bejlka.foodservice.security.SecurityUser;
import com.bejlka.foodservice.service.CartService;
import com.bejlka.foodservice.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {

    CartService cartService;
    UserService userService;

    @GetMapping()
    public CartDTO getCurrentCart(@AuthenticationPrincipal SecurityUser securityUser) {
        return cartService.getCart(userService.getUserByLogin(securityUser.getLogin()).getCart());
    }

    @PutMapping("/items/{id}")
    public CartDTO addItem(@AuthenticationPrincipal SecurityUser securityUser, @PathVariable("id") MenuItem menuItem) {
        return cartService.addItem(userService.getUserByLogin(securityUser.getLogin()), menuItem);
    }

    @DeleteMapping("/items/{id}")
    public void removeItem(@AuthenticationPrincipal SecurityUser securityUser, @PathVariable("id") CartItem cartItem) {
        cartService.remove(userService.getUserByLogin(securityUser.getLogin()), cartItem);
    }

    @DeleteMapping("/items")
    public void removeItems(@AuthenticationPrincipal SecurityUser securityUser) {
        cartService.removeAll(userService.getUserByLogin(securityUser.getLogin()).getCart());
    }
}
