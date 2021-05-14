package com.bejlka.foodservice.domain.mapper;

import com.bejlka.foodservice.domain.dto.*;
import com.bejlka.foodservice.domain.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO map(User user);
//
//    @Mapping(target = "userDTO", ignore = true)
//    CartDTO cartToDTO(Cart cart);
//
//    @Mapping(target = "restaurantDTO", ignore = true)
//    List<MenuItemDTO> menuItemToDTO(List<MenuItem> items);
//
//    @Mapping(target = "userDTO", ignore = true)
//    List<OrderDTO> orderToDTO(List<Order> orderList);
//
//    @Mapping(target = "items", ignore = true)
//    RestaurantDTO restaurantToDTO(Restaurant restaurant);

}
