package com.bejlka.foodservice.domain.mapper;


import com.bejlka.foodservice.domain.dto.OrderItemDTO;
import com.bejlka.foodservice.domain.entity.CartItem;
import com.bejlka.foodservice.domain.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "menuId", ignore = true)
    List<OrderItem> cartItemToOrderItem(List<CartItem> items);

    @Named("order.items")
    @Mapping(target = "restaurant", ignore = true)
    OrderItemDTO map(OrderItem orderItem);

    @Named("user.orders.item")
    @Mapping(target = "restaurant", ignore = true)
    OrderItemDTO userOrdersItemsToDTO(OrderItem orderItem);
}
