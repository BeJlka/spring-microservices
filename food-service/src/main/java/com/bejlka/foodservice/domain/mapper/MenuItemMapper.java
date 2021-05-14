package com.bejlka.foodservice.domain.mapper;

import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.entity.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    @Mapping(target = "restaurant", source = "restaurant")
    @Mapping(target = "restaurant.items", ignore = true)
    MenuItemDTO map(MenuItem menuItem);
}
