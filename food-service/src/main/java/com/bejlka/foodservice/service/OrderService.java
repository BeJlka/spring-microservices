package com.bejlka.foodservice.service;

import com.bejlka.foodservice.exeption.CustomException;
import com.bejlka.foodservice.feign.DeliveryServiceClient;
import com.bejlka.foodservice.feign.PaymentServiceClient;
import com.bejlka.foodservice.model.domain.entity.Cart;
import com.bejlka.foodservice.model.domain.entity.Order;
import com.bejlka.foodservice.model.domain.entity.User;
import com.bejlka.foodservice.model.dto.NotificationDTO;
import com.bejlka.foodservice.model.dto.OrderDTO;
import com.bejlka.foodservice.model.enums.Status;
import com.bejlka.foodservice.model.mapper.OrderItemMapper;
import com.bejlka.foodservice.model.mapper.OrderMapper;
import com.bejlka.foodservice.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    RuntimeService runtimeService;
    PaymentServiceClient paymentServiceClient;
    DeliveryServiceClient deliveryServiceClient;
    OrderRepository orderRepository;
    OrderItemMapper orderItemMapper;
    UserService userService;
    CartService cartService;
    OrderMapper orderMapper;

    @Transactional
    public OrderDTO createOrder(User user) {
        Cart cart = user.getCart();
        if (cart.getItems().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "В корзине ничего нет");
        }
        Order order = new Order();
        order.setUser(user);
        order.setAddress(user.getAddress());
        order.setRestaurant(cart.getItems().get(0).getRestaurant());
        order.setOrderDate(Instant.now());
        order.setStatus(Status.CREATE);
        orderRepository.save(order);
        orderItemMapper.cartItemToOrderItem(cart.getItems())
                .stream().peek(order::addOrderItemToOrder).collect(Collectors.toList());
        order.setAmount(order.getItems().stream().mapToDouble(item -> item.getPrice() * item.getCount()).sum());
        user.getOrders().add(order);
        cartService.removeAll(user.getCart());
        userService.updateUser(user);

        log.info("create order " + order.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", order.getId());
        runtimeService.startProcessInstanceByKey("notification", map);

        return orderMapper.map(order, null, null);
    }

    public List<OrderDTO> getAllOrder(User user) {
        return orderMapper.ListOrderToDTO(orderRepository.findByUserId(user.getId()));
    }

    public OrderDTO getOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            return orderMapper.map(optionalOrder.get(),
                    paymentServiceClient.getPayment(optionalOrder.get().getId()),
                    deliveryServiceClient.delivery(optionalOrder.get().getId()));
        }
        throw new CustomException(HttpStatus.NOT_FOUND, "Чек с таким id не найден: " + id);
    }

    public Order find(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        }
        throw new CustomException(HttpStatus.NOT_FOUND, "Не удалось найти счет: " + id);
    }

    public void update(Order order) {
        orderRepository.save(order);
    }

    public void updateStatusOrderCamunda(Long orderId) {
        Order order = find(orderId);
        if (order.getStatus().equals(Status.DONE)) {
            return;
        }
        if (order.getStatus().equals(Status.DELIVERY_DONE)) {
            order.setStatus(Status.DONE);
            update(order);
        }
    }

    public void cancelOrder(Long id) {
        Order order = find(id);
        order.setStatus(Status.CANCEL);
        update(order);
    }

    public void updateStatusOrder(Order order) {

        if (order.getStatus().equals(Status.DONE)) {
            return;
        }

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setEmail(order.getUser().getEmail());
        notificationDTO.setOrderId(order.getId());
    }
}
