package com.bejlka.foodservice.controller;

import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.service.MenuItemService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MenuItemController {

    MenuItemService menuItemService;

    @GetMapping("/{id}")
    public MenuItemDTO getMenuItem(@PathVariable Long id) {
        return menuItemService.getMenuItem(id);
    }

    @PostMapping()
    public MenuItemDTO createMenuItem(@RequestBody MenuItem menuItem) {
        return menuItemService.createMenuItemDTO(menuItem);
    }

    @PutMapping()
    public MenuItemDTO updateMenuItem(@RequestBody MenuItem menuItem) {
        return menuItemService.updateMenuItem(menuItem);
    }

    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
    }

}
