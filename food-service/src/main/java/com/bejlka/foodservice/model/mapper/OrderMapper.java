package com.bejlka.foodservice.model.mapper;

import com.bejlka.foodservice.model.domain.entity.Order;
import com.bejlka.foodservice.model.dto.DeliveryDTO;
import com.bejlka.foodservice.model.dto.OrderDTO;
import com.bejlka.foodservice.model.dto.PaymentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, OrderItemMapper.class, RestaurantMapper.class, PaymentMapper.class, DeliveryMapper.class})
public interface OrderMapper {
    @Mapping(target = "user", qualifiedByName = "order.user")
    @Mapping(target = "items", qualifiedByName = "order.items")
    @Mapping(target = "restaurant", qualifiedByName = "items.restaurant")
    @Mapping(target = "payment", qualifiedByName = "ignorePayment")
    @Mapping(target = "delivery", qualifiedByName = "ignoreDelivery")
    @Mapping(target = "status", source = "order.status")
    @Mapping(target = "orderDate", source = "order.orderDate")
    @Mapping(target = "deliveryDate", source = "order.deliveryDate")
    OrderDTO map(Order order, PaymentDTO payment, DeliveryDTO delivery);

    @Mapping(target = "user", qualifiedByName = "order.user")
    @Mapping(target = "items", qualifiedByName = "order.items")
    @Mapping(target = "restaurant", qualifiedByName = "items.restaurant")
    List<OrderDTO> ListOrderToDTO(List<Order> orders);

    @Named("user.orders")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "payment", ignore = true)
    @Mapping(target = "delivery", ignore = true)
    @Mapping(target = "restaurant", qualifiedByName = "items.restaurant")
    @Mapping(target = "items", qualifiedByName = "user.orders.item")
    OrderDTO userOrderToDTO(Order order);
}
