package com.bejlka.foodservice.model.mapper;

import com.bejlka.foodservice.model.domain.entity.Restaurant;
import com.bejlka.foodservice.model.dto.RestaurantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {MenuItemMapper.class})
public interface RestaurantMapper {
    @Mapping(target = "items", qualifiedByName = "restaurant.items")
    RestaurantDTO restaurantToDTO(Restaurant restaurant);

    @Named("items.restaurant")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    RestaurantDTO itemRestaurantToDTO(Restaurant restaurant);

}
