package com.bejlka.foodservice.domain.mapper;

import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.entity.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface MenuItemMapper {
    @Mapping(target = "restaurant", qualifiedByName = "items.restaurant")
    MenuItemDTO map(MenuItem menuItem);

    @Named("restaurant.items")
    @Mapping(target = "restaurant", ignore = true)
    MenuItemDTO restaurantItemsToDTO(MenuItem menuItem);
}
