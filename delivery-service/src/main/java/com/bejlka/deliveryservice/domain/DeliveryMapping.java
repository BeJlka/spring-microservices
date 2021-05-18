package com.bejlka.deliveryservice.domain;

import com.bejlka.deliveryservice.domain.dto.DeliveryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DeliveryMapping {
    @Mapping(target = "restaurantAddress", source = "restaurantAddress")
    @Mapping(target = "deliveryAddress", source = "deliveryAddress")
    @Mapping(target = "orderDate", source = "orderDate")
    @Mapping(target = "deliveryDate", source = "deliveryDate")
    DeliveryDTO DeliveryToDTO(Delivery delivery);

    @Mapping(target = "restaurantAddress", source = "restaurantAddress")
    @Mapping(target = "deliveryAddress", source = "deliveryAddress")
    @Mapping(target = "orderDate", source = "orderDate")
    @Mapping(target = "deliveryDate", source = "deliveryDate")
    List<DeliveryDTO> listDeliveryToDTO(List<Delivery> deliveries);
}
