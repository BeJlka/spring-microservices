package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.dto.DeliveryDTO;
import com.bejlka.foodservice.domain.dto.NotificationDTO;
import com.bejlka.foodservice.domain.dto.PaymentDTO;
import com.bejlka.foodservice.domain.dto.UserDTO;
import com.bejlka.foodservice.domain.entity.Order;
import com.bejlka.foodservice.domain.entity.User;
import com.bejlka.foodservice.domain.enums.Role;
import com.bejlka.foodservice.domain.enums.Status;
import com.bejlka.foodservice.domain.mapper.UserMapper;
import com.bejlka.foodservice.exeption.CustomException;
import com.bejlka.foodservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    PaymentService paymentService;
    DeliveryService deliveryService;
    PasswordEncoder passwordEncoder;
    RabbitMQService rabbitMQService;
    OrderService orderService;
    CartService cartService;
    UserMapper userMapper;

    @Transactional
    public void createOrder(User user) {
        if (user.getCart() == null || user.getCart().getItems().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "В корзине ничего нет");
        }
        if (user.getOrders() == null) {
            user.setOrders(new ArrayList<>());
        }
        Order order = orderService.createOrder(user);
        user.getOrders().add(order);
        cartService.removeAll(user.getCart());
        updateUser(user);

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setEmail(user.getEmail());
        notificationDTO.setOrderId(order.getId());
        notificationDTO.setTitle("Оформление заказа");

        PaymentDTO payment = paymentService.createPayment(user, order);
        if (payment.getStatus().equals("SUCCESS")) {
            notificationDTO.setMessage("Оплата прошла успешно");
            rabbitMQService.sendMessage(notificationDTO);
            order.setStatus(Status.COOKING);
            orderService.update(order);
        } else {
            notificationDTO.setMessage("Что-то пошло не так во вроемя оплаты. Попробуйте еще раз");
            rabbitMQService.sendMessage(notificationDTO);
        }
    }

    public User getUserByLogin(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new CustomException(HttpStatus.NOT_FOUND, "Пользователь не найден: " + login);
    }

    public UserDTO getUserByLoginDTO(String login) {
        return userMapper.userToDTO(getUserByLogin(login));
    }

    public Long createUser(User user) {
        Optional<User> optionalUser = userRepository.findByLogin(user.getLogin());
        if (optionalUser.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Логин занят: " + user.getLogin());
        }
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }

    public UserDTO updateUser(User user) {
        return userMapper.userToDTO(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void confirmation(Order order) {
        DeliveryDTO delivery = deliveryService.delivery(order.getId());
        if (delivery.getStatus().equals("DONE")) {

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setEmail(order.getUser().getEmail());
            notificationDTO.setOrderId(order.getId());
            notificationDTO.setTitle("Отзыв");
            notificationDTO.setMessage("Пожалуйста оцените качество доставки и оставьте свой отзыв.");

            rabbitMQService.sendMessage(notificationDTO);
            order.setStatus(Status.DONE);
            order.setDeliveryDate(delivery.getDeliveryDate());
            orderService.update(order);
        }
    }
}
