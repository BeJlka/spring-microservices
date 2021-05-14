package com.bejlka.foodservice.domain.mapper;

import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.dto.RestaurantDTO;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.domain.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantDTO map(Restaurant restaurant);

    @Mapping(target = "restaurant", ignore = true)
    MenuItemDTO map(MenuItem menuItem);
}
