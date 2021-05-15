package com.bejlka.foodservice.controller;

import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.service.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
@AllArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

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
