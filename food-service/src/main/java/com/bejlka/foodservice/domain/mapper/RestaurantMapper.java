package com.bejlka.foodservice.domain.mapper;

import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.dto.RestaurantDTO;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.domain.entity.Restaurant;
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
