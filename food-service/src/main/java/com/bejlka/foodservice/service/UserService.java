package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.dto.UserDTO;
import com.bejlka.foodservice.domain.entity.User;
import com.bejlka.foodservice.domain.enums.Role;
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
    PasswordEncoder passwordEncoder;
    OrderService orderService;
    CartService cartService;
    UserMapper userMapper;

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new CustomException(HttpStatus.NOT_FOUND, "Пользователь не найден: " + id);
    }

    @Transactional
    public void createOrder(User user) {
        if (user.getCart() == null || user.getCart().getItems().isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "В корзине ничего нет");
        }
        if (user.getOrders() == null) {
            user.setOrders(new ArrayList<>());
        }
        user.getOrders().add(orderService.createOrder(user));
        cartService.removeAll(user.getCart());
        updateUser(user);
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
}
