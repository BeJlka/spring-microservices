package com.bejlka.foodservice.domain.mapper;

import com.bejlka.foodservice.domain.dto.UserDTO;
import com.bejlka.foodservice.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {CartMapper.class, OrderMapper.class})
public interface UserMapper {
    @Mapping(target = "cart", qualifiedByName = "user.cart")
    @Mapping(target = "orders", qualifiedByName = "user.orders")
    UserDTO userToDTO(User user);

    @Named("order.user")
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "orders", ignore = true)
    UserDTO orderUserToDTO(User user);
}
