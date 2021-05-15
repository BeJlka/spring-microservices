package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.entity.CartItem;
import com.bejlka.foodservice.domain.entity.OrderItem;
import com.bejlka.foodservice.domain.mapper.OrderItemMapper;
import com.bejlka.foodservice.repository.OrderItemRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderItemService {
    OrderItemRepository orderItemRepository;
    OrderItemMapper orderItemMapper;


    public List<OrderItem> saveAll(List<CartItem> items) {
        return orderItemRepository.saveAll(orderItemMapper.cartItemToOrderItem(items));
    }
}
