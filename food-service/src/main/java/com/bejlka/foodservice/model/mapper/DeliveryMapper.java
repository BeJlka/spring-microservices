package com.bejlka.foodservice.model.mapper;

import com.bejlka.foodservice.model.dto.DeliveryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    @Named("ignoreDelivery")
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    DeliveryDTO ignore(DeliveryDTO delivery);
}
