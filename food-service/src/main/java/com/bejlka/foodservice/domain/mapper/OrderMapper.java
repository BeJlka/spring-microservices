package com.bejlka.foodservice.domain.mapper;

import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.dto.OrderDTO;
import com.bejlka.foodservice.domain.dto.OrderItemDTO;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.domain.entity.Order;
import com.bejlka.foodservice.domain.entity.OrderItem;
import com.bejlka.foodservice.domain.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {UserMapper.class, OrderItemMapper.class, RestaurantMapper.class})
public interface OrderMapper {
    @Mapping(target = "user", qualifiedByName = "order.user")
    @Mapping(target = "items", qualifiedByName = "order.items")
    @Mapping(target = "restaurant", qualifiedByName = "items.restaurant")
    OrderDTO map(Order order);

    @Named("user.orders")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "restaurant", qualifiedByName = "items.restaurant")
    @Mapping(target = "items", qualifiedByName = "user.orders.item")
    OrderDTO userOrderToDTO(Order order);
}
