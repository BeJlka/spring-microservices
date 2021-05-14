package com.bejlka.foodservice.service;

import com.bejlka.foodservice.domain.dto.MenuItemDTO;
import com.bejlka.foodservice.domain.dto.RestaurantDTO;
import com.bejlka.foodservice.domain.entity.MenuItem;
import com.bejlka.foodservice.domain.entity.Restaurant;
import com.bejlka.foodservice.domain.mapper.MenuItemMapper;
import com.bejlka.foodservice.domain.mapper.RestaurantMapper;
import com.bejlka.foodservice.exeption.NameBusy;
import com.bejlka.foodservice.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemService menuItemService;
    private final RestaurantMapper restaurantMapper;
    private final MenuItemMapper menuItemMapper;

    public RestaurantDTO getRestaurant(Restaurant restaurant) {
        return restaurantMapper.map(restaurant);
    }

    public List<MenuItemDTO> getAllMenuItem(Restaurant restaurant) {
        return restaurant.getItems().stream().map(menuItemMapper::map).collect(Collectors.toList());
    }

    public Long createRestaurant(Restaurant restaurant) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findByName(restaurant.getName());
        if (optionalRestaurant.isPresent()){
            throw new NameBusy("Данное название ресторана занято");
        }
        return restaurantRepository.save(restaurant).getId();
    }

    public void updateRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public RestaurantDTO createAndAddMenuItem(Restaurant restaurant, MenuItem menuItem) {
        MenuItem saveMenuItem = menuItemService.createMenuItem(menuItem);
        restaurant.getItems().add(saveMenuItem);
        saveMenuItem.setRestaurant(restaurant);
        menuItemService.updateMenuItem(saveMenuItem);
        return restaurantMapper.map(restaurantRepository.save(restaurant));
    }

    public void removeMenuItem(Restaurant restaurant, MenuItem menuItem) {
        restaurant.getItems().remove(menuItem);
        menuItemService.deleteMenuItem(menuItem.getId());
        restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
}
