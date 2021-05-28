package com.bejlka.foodservice.controller;

import com.bejlka.foodservice.model.domain.entity.Order;
import com.bejlka.foodservice.model.dto.OrderDTO;
import com.bejlka.foodservice.security.SecurityUser;
import com.bejlka.foodservice.service.OrderService;
import com.bejlka.foodservice.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;
    UserService userService;

    @GetMapping()
    public List<OrderDTO> getAllOrder(@AuthenticationPrincipal SecurityUser securityUser) {
        return orderService.getAllOrder(userService.getUserByLogin(securityUser.getLogin()));
    }

    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

//    @GetMapping("/repeat/{id}")
//    public OrderDTO orderRepeatPayment(@PathVariable("id") Order order) {
//        return orderService.orderRepeatPayment(order);
//    }

    @PostMapping()
    public OrderDTO createOrder(@AuthenticationPrincipal SecurityUser securityUser) {
        return orderService.createOrder(userService.getUserByLogin(securityUser.getLogin()));
    }

    @PutMapping("/{id}")
    public void updateStatusOrder(@PathVariable("id") Order order) {
        orderService.updateStatusOrder(order);
    }
}
