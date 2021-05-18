package com.bejlka.foodservice.domain.mapper;

import com.bejlka.foodservice.domain.dto.DeliveryDTO;
import com.bejlka.foodservice.domain.dto.PaymentDTO;
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
