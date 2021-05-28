package com.bejlka.foodservice.model.mapper;

import com.bejlka.foodservice.model.domain.entity.User;
import com.bejlka.foodservice.model.dto.UserDTO;
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
