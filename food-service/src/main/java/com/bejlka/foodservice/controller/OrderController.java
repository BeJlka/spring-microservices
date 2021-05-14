package com.bejlka.foodservice.controller;

import com.bejlka.foodservice.domain.dto.OrderDTO;
import com.bejlka.foodservice.exeption.OrderNotFound;
import com.bejlka.foodservice.exeption.UserNotFound;
import com.bejlka.foodservice.security.SecurityUser;
import com.bejlka.foodservice.service.OrderService;
import com.bejlka.foodservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping()
    public List<OrderDTO> getAllOrder(@AuthenticationPrincipal SecurityUser securityUser) {
        return orderService.getAllOrder(userService.getUserByLogin(securityUser.getLogin()));
    }

    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }
}
