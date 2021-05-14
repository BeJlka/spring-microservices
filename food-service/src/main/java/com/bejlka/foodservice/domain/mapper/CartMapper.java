package com.bejlka.foodservice.domain.mapper;

import com.bejlka.foodservice.domain.dto.CartDTO;
import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.dto.UserDTO;
import com.bejlka.foodservice.domain.entity.Cart;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "user.cart", ignore = true)
    @Mapping(target = "user.orderList", ignore = true)
    CartDTO map(Cart cart);

    @Mapping(target = "restaurant.items", ignore = true)
    MenuItemDTO map (MenuItem menuItem);
}
