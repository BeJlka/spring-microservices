package com.bejlka.foodservice.model.mapper;

import com.bejlka.foodservice.model.domain.entity.MenuItem;
import com.bejlka.foodservice.model.dto.MenuItemDTO;
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
