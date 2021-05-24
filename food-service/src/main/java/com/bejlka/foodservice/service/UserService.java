package com.bejlka.foodservice.service;

import com.bejlka.foodservice.exeption.CustomException;
import com.bejlka.foodservice.model.domain.entity.Cart;
import com.bejlka.foodservice.model.domain.entity.User;
import com.bejlka.foodservice.model.dto.UserDTO;
import com.bejlka.foodservice.model.enums.Role;
import com.bejlka.foodservice.model.mapper.UserMapper;
import com.bejlka.foodservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Пользователь не найден: " + login));
    }

    public UserDTO getUserByLoginDTO(String login) {
        return userMapper.userToDTO(getUserByLogin(login));
    }

    public Long createUser(User user) {
        Optional<User> optionalUser = userRepository.findByLogin(user.getLogin());
        if (optionalUser.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Логин занят: " + user.getLogin());
        }
        user.setCart(new Cart());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public UserDTO updateUserDTO(User user) {
        return userMapper.userToDTO(updateUser(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
