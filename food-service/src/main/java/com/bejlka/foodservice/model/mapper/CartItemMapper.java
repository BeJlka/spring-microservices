package com.bejlka.foodservice.model.mapper;

import com.bejlka.foodservice.model.domain.entity.CartItem;
import com.bejlka.foodservice.model.dto.CartItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface CartItemMapper {
    @Named("user.cart.items")
    @Mapping(target = "restaurant", qualifiedByName = "items.restaurant")
    CartItemDTO cartItemToDTO(CartItem item);
}
