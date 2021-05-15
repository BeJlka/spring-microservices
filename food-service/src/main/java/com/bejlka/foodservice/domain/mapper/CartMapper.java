package com.bejlka.foodservice.domain.mapper;

import com.bejlka.foodservice.domain.dto.CartDTO;
import com.bejlka.foodservice.domain.dto.CartItemDTO;
import com.bejlka.foodservice.domain.entity.Cart;
import com.bejlka.foodservice.domain.entity.CartItem;
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
