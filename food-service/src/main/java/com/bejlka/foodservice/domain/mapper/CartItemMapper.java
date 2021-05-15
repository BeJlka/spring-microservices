package com.bejlka.foodservice.domain.mapper;

import com.bejlka.foodservice.domain.dto.CartItemDTO;
import com.bejlka.foodservice.domain.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface CartItemMapper {
    @Named("user.cart.items")
    @Mapping(target = "restaurant", qualifiedByName = "items.restaurant")
    CartItemDTO cartItemToDTO(CartItem item);
}
