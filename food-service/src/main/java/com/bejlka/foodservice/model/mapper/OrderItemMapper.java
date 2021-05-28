package com.bejlka.foodservice.model.mapper;


import com.bejlka.foodservice.model.domain.entity.CartItem;
import com.bejlka.foodservice.model.domain.entity.OrderItem;
import com.bejlka.foodservice.model.dto.OrderItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "menuId", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "cartId", ignore = true)
    List<OrderItem> cartItemToOrderItem(List<CartItem> items);

    @Named("order.items")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    OrderItemDTO map(OrderItem orderItem);

    @Named("user.orders.item")
    @Mapping(target = "restaurant", ignore = true)
    OrderItemDTO userOrdersItemsToDTO(OrderItem orderItem);
}
