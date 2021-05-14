package com.bejlka.foodservice.domain.mapper;

import com.bejlka.foodservice.domain.dto.*;
import com.bejlka.foodservice.domain.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO map(User user);

    @Mapping(target = "user", ignore = true)
    CartDTO map(Cart cart);

    @Mapping(target = "restaurant", ignore = true)
    MenuItemDTO map(MenuItem menuItem);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "restaurant.items", ignore = true)
    OrderDTO map(Order order);

}
