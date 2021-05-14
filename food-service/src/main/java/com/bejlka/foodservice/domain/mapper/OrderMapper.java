package com.bejlka.foodservice.domain.mapper;

import com.bejlka.foodservice.domain.dto.OrderDTO;
import com.bejlka.foodservice.domain.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO map(Order order);
}
