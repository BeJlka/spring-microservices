package com.bejlka.foodservice.domain.mapper;

import com.bejlka.foodservice.domain.dto.CartDTO;
import com.bejlka.foodservice.domain.dto.UserDTO;
import com.bejlka.foodservice.domain.entity.Cart;
import com.bejlka.foodservice.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDTO map(Cart cart);
}
