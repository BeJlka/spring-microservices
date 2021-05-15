package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.dto.OrderDTO;
import com.bejlka.foodservice.domain.entity.Order;
import com.bejlka.foodservice.domain.entity.User;
import com.bejlka.foodservice.domain.enums.Status;
import com.bejlka.foodservice.domain.mapper.OrderMapper;
import com.bejlka.foodservice.exeption.CustomException;
import com.bejlka.foodservice.exeption.OrderNotFound;
import com.bejlka.foodservice.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;
    OrderItemService orderItemService;
    OrderMapper orderMapper;

    @Transactional
    public Order createOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setAddress(user.getAddress());
        order.setRestaurant(user.getCart().getItems().get(0).getRestaurant());
        order.getItems().addAll(orderItemService.saveAll(user.getCart().getItems()));
        order.setAmount(order.getItems().stream().mapToDouble(item -> item.getPrice() * item.getCount()).sum());
        order.setOrderDate(Instant.now());
        order.setStatus(Status.CREATE);
        return orderRepository.save(order);
    }

    public List<OrderDTO> getAllOrder(User user) {
        return user.getOrders().stream().map(orderMapper::map).collect(Collectors.toList());
    }
//
//    public Order addItem(User user, MenuItem menuItem) {
//        Order order;
//        Optional<Order> optionalOrder = user.getOrders().stream().filter(item ->
//                item.getRestaurant().getId().equals(menuItem.getRestaurant().getId()) && !item.isPayment()
//        ).findFirst();
//
//        if (optionalOrder.isPresent()) {
//            order = optionalOrder.get();
//            Optional<OrderItem> optionalOrderItem = order.getItems().stream().filter(orderItem -> orderItem.getName().equals(menuItem.getName()))
//                    .findFirst();
//            if (optionalOrderItem.isPresent()) {
//                optionalOrderItem.get().increment();
//            } else {
//                order.getItems().add(orderItemService.create(menuItem));
//            }
//        } else {
////            order = createOrder(user, menuItem);
//        }
////        return orderRepository.save(order);
//        return null;
//    }

    public OrderDTO getOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            return orderMapper.map(optionalOrder.get());
        }
        throw new CustomException(HttpStatus.NOT_FOUND, "Чек с таким id не найден: " + id);
    }
}
