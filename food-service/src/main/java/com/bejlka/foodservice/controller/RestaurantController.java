package com.bejlka.foodservice.controller;

import com.bejlka.foodservice.model.domain.entity.MenuItem;
import com.bejlka.foodservice.model.domain.entity.Restaurant;
import com.bejlka.foodservice.model.dto.MenuItemDTO;
import com.bejlka.foodservice.model.dto.RestaurantDTO;
import com.bejlka.foodservice.service.RestaurantService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestaurantController {

    RestaurantService restaurantService;

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
    public RestaurantDTO createAndAddMenuItem(@PathVariable("id") Restaurant restaurant, @RequestBody MenuItem menuItem) {
        return restaurantService.createAndAddMenuItem(restaurant, menuItem);
    }

    @DeleteMapping("/{id}/menu")
    public void removeMenuItem(@PathVariable("id") Restaurant restaurant, @RequestBody MenuItem menuItem) {
        restaurantService.removeMenuItem(restaurant, menuItem);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }
}
