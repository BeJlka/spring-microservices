package com.bejlka.foodservice.model.mapper;

import com.bejlka.foodservice.model.dto.PaymentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Named("ignorePayment")
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    PaymentDTO ignore(PaymentDTO payment);
}
