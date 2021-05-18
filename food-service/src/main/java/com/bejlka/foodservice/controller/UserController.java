package com.bejlka.foodservice.controller;

import com.bejlka.foodservice.domain.dto.UserDTO;
import com.bejlka.foodservice.domain.entity.Order;
import com.bejlka.foodservice.domain.entity.User;
import com.bejlka.foodservice.security.SecurityUser;
import com.bejlka.foodservice.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping()
    public UserDTO getCurrentUser(@AuthenticationPrincipal SecurityUser securityUser) {
        return userService.getUserByLoginDTO(securityUser.getLogin());
    }

    @GetMapping("/order")
    public void createOrder(@AuthenticationPrincipal SecurityUser securityUser) {
        userService.createOrder(userService.getUserByLogin(securityUser.getLogin()));
    }

    @GetMapping("/confirmation/{id}")
    public void confirmation(@AuthenticationPrincipal SecurityUser securityUser, @PathVariable("id") Order order) {
        userService.confirmation(order);
    }

    @PostMapping("/registration")
    public Long createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping()
    public UserDTO updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


}
