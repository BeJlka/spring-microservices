package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.domain.mapper.MenuItemMapper;
import com.bejlka.foodservice.exeption.MenuItemNotFound;
import com.bejlka.foodservice.repository.MenuItemRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MenuItemService {

    MenuItemRepository menuItemRepository;
    MenuItemMapper menuItemMapper;

    public MenuItem findMenuById(Long id) {
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(id);
        if (optionalMenuItem.isPresent()) {
            return optionalMenuItem.get();
        }
        throw new MenuItemNotFound("Пункт меню с таким id не найден: " + id);
    }

    public MenuItemDTO getMenuItem(Long id) {
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(id);
        if (optionalMenuItem.isPresent()) {
            return menuItemMapper.map(optionalMenuItem.get());
        }
        throw new MenuItemNotFound("Пункт меню с таким id не найден: " + id);
    }

    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public MenuItemDTO createMenuItemDTO(MenuItem menuItem) {
        return menuItemMapper.map(createMenuItem(menuItem));
    }

    public MenuItemDTO updateMenuItem(MenuItem menuItem) {
        return menuItemMapper.map(menuItemRepository.save(menuItem));
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}
