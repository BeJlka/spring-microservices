package com.bejlka.foodservice.controller;

import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.dto.RestaurantDTO;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.domain.entity.Restaurant;
import com.bejlka.foodservice.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public RestaurantDTO getRestaurant(@PathVariable("id") Restaurant restaurant) {
        return restaurantService.getRestaurant(restaurant);
    }

    @GetMapping("/{id}/menu")
    public List<MenuItemDTO> getAllMenuItem(@PathVariable("id") Restaurant restaurant) {
        return restaurantService.getRestaurant(restaurant).getItems();
    }

    @PostMapping()
    public Long createRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.createRestaurant(restaurant);
    }

    @PutMapping("/{id}")
    public void updateRestaurant(@RequestBody Restaurant restaurant) {
        restaurantService.updateRestaurant(restaurant);
    }

    @PutMapping("/{id}/menu")
    public void addMenuItem(@PathVariable("id") Restaurant restaurant, @RequestBody MenuItem menuItem) {
        restaurantService.addMenuItem(restaurant, menuItem);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }
}
