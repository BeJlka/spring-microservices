package com.bejlka.foodservice.model.mapper;

import com.bejlka.foodservice.model.dto.CartDTO;
import com.bejlka.foodservice.model.domain.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
    @Named("user.cart")
    @Mapping(target = "items", qualifiedByName = "user.cart.items")
    CartDTO userCartToDTO(Cart cart);

    @Mapping(target = "items", qualifiedByName = "user.cart.items")
    CartDTO cartToDTO(Cart cart);

//    @Mapping(target = "restaurant.items", ignore = true)
//    CartItemDTO cartItemToDTO(CartItem cartItem);
}
