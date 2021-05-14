package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.dto.OrderDTO;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.domain.entity.Order;
import com.bejlka.foodservice.domain.entity.User;
import com.bejlka.foodservice.domain.mapper.OrderMapper;
import com.bejlka.foodservice.exeption.OrderNotFound;
import com.bejlka.foodservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Order createOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setAddress(user.getAddress());
        order.setItems(user.getCart().getItems());
        order.setAmount(order.getItems().stream().mapToDouble(MenuItem::getPrice).sum());
        order.setRestaurant(user.getCart().getRestaurant());
        order.setOrderDate(Instant.now());
        return orderRepository.save(order);
    }

    public List<OrderDTO> getAllOrder(User user) {
        return user.getOrderList().stream().map(orderMapper::map).collect(Collectors.toList());
    }

    public OrderDTO getOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            return orderMapper.map(optionalOrder.get());
        }
        throw new OrderNotFound("Чек с таким id не найден: " + id);
    }
}
