package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.dto.UserDTO;
import com.bejlka.foodservice.domain.entity.User;
import com.bejlka.foodservice.domain.enums.Role;
import com.bejlka.foodservice.domain.mapper.UserMapper;
import com.bejlka.foodservice.exeption.CartIsEmpty;
import com.bejlka.foodservice.exeption.LoginBusy;
import com.bejlka.foodservice.exeption.UserNotFound;
import com.bejlka.foodservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        throw new UserNotFound("Пользователь не найден");
    }

    public void createOrder(User user) {
        if (user.getCart().getItems().size() == 0) {
            throw new CartIsEmpty("В корзине ничего нет");
        }
        user.getOrderList().add(orderService.createOrder(user));
        updateUser(user);
    }

    public User getUserByLogin(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserNotFound("Пользователь не найден");
    }

    public UserDTO getUserByLoginDTO(String login) {
        return userMapper.map(getUserByLogin(login));
    }

    public Long createUser(User user) {
        Optional<User> optionalUser = userRepository.findByLogin(user.getLogin());
        if (optionalUser.isPresent()) {
            throw new LoginBusy("Логин занят");
        }
        user.setCart(cartService.createCart());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }

    public UserDTO updateUser(User user) {
        return userMapper.map(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
