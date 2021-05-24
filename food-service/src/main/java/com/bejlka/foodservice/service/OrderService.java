package com.bejlka.foodservice.service;

import com.bejlka.foodservice.exeption.CustomException;
import com.bejlka.foodservice.model.domain.entity.Cart;
import com.bejlka.foodservice.model.domain.entity.Order;
import com.bejlka.foodservice.model.domain.entity.User;
import com.bejlka.foodservice.model.dto.DeliveryDTO;
import com.bejlka.foodservice.model.dto.NotificationDTO;
import com.bejlka.foodservice.model.dto.OrderDTO;
import com.bejlka.foodservice.model.dto.PaymentDTO;
import com.bejlka.foodservice.model.enums.Status;
import com.bejlka.foodservice.model.mapper.OrderItemMapper;
import com.bejlka.foodservice.model.mapper.OrderMapper;
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
    OrderItemMapper orderItemMapper;
    UserService userService;
    CartService cartService;
    PaymentService paymentService;
    RabbitMQService rabbitMQService;
    DeliveryService deliveryService;
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
        order.getItems().addAll(orderItemMapper.cartItemToOrderItem(cart.getItems())
                .stream().peek(orderItem -> orderItem.setOrder(order)).collect(Collectors.toList()));
        order.setAmount(order.getItems().stream().mapToDouble(item -> item.getPrice() * item.getCount()).sum());
        order.setOrderDate(Instant.now());
        order.setStatus(Status.CREATE);
        user.getOrders().add(order);
        cartService.removeAll(user.getCart());
        userService.updateUser(user);

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setEmail(user.getEmail());
        notificationDTO.setOrderId(user.getOrders().get(user.getOrders().size() - 1).getId());

        notificationDTO.setTitle("Оформление заказа");
        PaymentDTO payment = paymentService.createPayment(user.getId(), user.getOrders().get(user.getOrders().size() - 1).getId());
        if (payment.getStatus().equals("SUCCESS")) {
            notificationDTO.setMessage("Оплата прошла успешно");
            rabbitMQService.sendMessage(notificationDTO);
            order.setStatus(Status.COOKING);
        } else {
            notificationDTO.setMessage("Что-то пошло не так во вроемя оплаты. Попробуйте еще раз");
            rabbitMQService.sendMessage(notificationDTO);
        }

        return orderMapper.map(order, payment, null);
    }

    @Transactional
    public OrderDTO orderRepeatPayment(Order order) {
        PaymentDTO paymentDTO = paymentService.updatePayment(order.getId());
        if (paymentDTO.getOrderId().equals(order.getId())) {
            order.setStatus(Status.COOKING);
            update(order);

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setEmail(order.getUser().getEmail());
            notificationDTO.setOrderId(order.getId());
            notificationDTO.setTitle("Оплата");
            notificationDTO.setMessage("Оплата прошла успешно и ресторан приступил к готовке");

            rabbitMQService.sendMessage(notificationDTO);
        }
        return orderMapper.map(order, paymentDTO, null);
    }

    public List<OrderDTO> getAllOrder(User user) {
        return orderMapper.ListOrderToDTO(orderRepository.findByUserId(user.getId()));
    }

    public OrderDTO getOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            return orderMapper.map(optionalOrder.get(),
                    paymentService.payment(optionalOrder.get().getId()),
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

        if (order.getStatus().equals(Status.DONE)) {
            return;
        }

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setEmail(order.getUser().getEmail());
        notificationDTO.setOrderId(order.getId());

        if (order.getStatus().equals(Status.DELIVERY)) {
            DeliveryDTO delivery = deliveryService.delivery(order.getId());
            if (delivery.getStatus().equals("DONE")) {

                notificationDTO.setTitle("Отзыв");
                notificationDTO.setMessage("Пожалуйста оцените качество доставки и оставьте свой отзыв.");

                rabbitMQService.sendMessage(notificationDTO);
                order.setStatus(Status.DONE);
                order.setDeliveryDate(delivery.getDeliveryDate());
                update(order);
            }
        }

        if (order.getStatus().equals(Status.COOKING)) {
            order.setStatus(Status.DELIVERY);

            notificationDTO.setTitle("Ресторан");
            notificationDTO.setMessage("Ресторан закончил готовить заказ и передал его службе доставки");

            rabbitMQService.sendMessage(notificationDTO);
            deliveryService.createDelivery(order);
            update(order);
        }

    }
}
