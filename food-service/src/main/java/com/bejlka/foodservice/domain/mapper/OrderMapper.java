package com.bejlka.foodservice.domain.mapper;

import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.dto.OrderDTO;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.domain.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "user.cart", ignore = true)
    @Mapping(target = "user.orderList", ignore = true)
    @Mapping(target = "restaurant.items", ignore = true)
    OrderDTO map(Order order);

    @Mapping(target = "restaurant", ignore = true)
    MenuItemDTO map(MenuItem menuItem);
}
