package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.dto.NotificationDTO;
import com.bejlka.foodservice.domain.dto.OrderDTO;
import com.bejlka.foodservice.domain.entity.Order;
import com.bejlka.foodservice.domain.entity.User;
import com.bejlka.foodservice.domain.enums.Status;
import com.bejlka.foodservice.domain.mapper.OrderMapper;
import com.bejlka.foodservice.exeption.CustomException;
import com.bejlka.foodservice.feign.PaymentServiceClient;
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
    PaymentServiceClient paymentServiceClient;
    RabbitMQService rabbitMQService;
    DeliveryService deliveryService;
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
        return user.getOrders().stream().map(order -> orderMapper.map(order,
                paymentServiceClient.getPayment(order.getId()),
                deliveryService.delivery(order.getId()))).collect(Collectors.toList());
    }

    public OrderDTO getOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            return orderMapper.map(optionalOrder.get(),
                    paymentServiceClient.getPayment(optionalOrder.get().getId()),
                    deliveryService.delivery(optionalOrder.get().getId()));
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

    public void updateStatusOrder(Order order) {
        if (order.getStatus().equals(Status.COOKING)) {
            order.setStatus(Status.DELIVERY);

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setEmail(order.getUser().getEmail());
            notificationDTO.setOrderId(order.getId());
            notificationDTO.setTitle("Ресторан");
            notificationDTO.setMessage("Ресторан закончил готовить заказ и передал его службе доставки");

            rabbitMQService.sendMessage(notificationDTO);
            deliveryService.createDelivery(order);
            orderRepository.save(order);
        }
    }
}
