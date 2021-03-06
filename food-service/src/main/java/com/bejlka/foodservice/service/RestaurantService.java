package com.bejlka.foodservice.service;

import com.bejlka.foodservice.exeption.CustomException;
import com.bejlka.foodservice.model.domain.entity.MenuItem;
import com.bejlka.foodservice.model.domain.entity.Restaurant;
import com.bejlka.foodservice.model.dto.RestaurantDTO;
import com.bejlka.foodservice.model.mapper.RestaurantMapper;
import com.bejlka.foodservice.repository.RestaurantRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestaurantService {

    RestaurantRepository restaurantRepository;
    RestaurantMapper restaurantMapper;

    public RestaurantDTO getRestaurant(Restaurant restaurant) {
        return restaurantMapper.restaurantToDTO(restaurant);
    }

    public Long createRestaurant(Restaurant restaurant) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findByName(restaurant.getName());
        if (optionalRestaurant.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Данное название ресторана занято");
        }
        return restaurantRepository.save(restaurant).getId();
    }

    public void updateRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public RestaurantDTO createAndAddMenuItem(Restaurant restaurant, MenuItem menuItem) {
        menuItem.setRestaurant(restaurant);
        restaurant.getItems().add(menuItem);
        return restaurantMapper.restaurantToDTO(restaurantRepository.save(restaurant));
    }

    public void removeMenuItem(Restaurant restaurant, MenuItem menuItem) {
        restaurant.getItems().remove(menuItem);
        restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
}
