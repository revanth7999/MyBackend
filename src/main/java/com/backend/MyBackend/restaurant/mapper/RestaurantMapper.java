package com.backend.MyBackend.restaurant.mapper;

import com.backend.MyBackend.restaurant.dto.RestaurantDto;
import com.backend.MyBackend.restaurant.entity.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper{

    public Restaurant toEntity(RestaurantDto dto){

        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.getName());
        restaurant.setCuisine(dto.getCuisine());
        restaurant.setIsOpen(dto.getIsOpen());
        restaurant.setPhone(dto.getPhone());
        restaurant.setRating(dto.getRating());
        restaurant.setAddress(dto.getAddress());
        restaurant.setDishes(dto.getDishes());

        return restaurant;
    }

    public RestaurantDto toDto(Restaurant restaurant){

        RestaurantDto dto = new RestaurantDto();
        dto.setName(restaurant.getName());
        dto.setCuisine(restaurant.getCuisine());
        dto.setIsOpen(restaurant.getIsOpen());
        dto.setPhone(restaurant.getPhone());
        dto.setRating(restaurant.getRating());
        dto.setAddress(restaurant.getAddress());
        dto.setDishes(restaurant.getDishes());

        return dto;
    }
}
