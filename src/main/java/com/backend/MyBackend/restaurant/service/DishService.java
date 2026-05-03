package com.backend.MyBackend.restaurant.service;

import com.backend.MyBackend.restaurant.entity.Dishes;
import com.backend.MyBackend.restaurant.entity.Restaurant;
import com.backend.MyBackend.restaurant.repository.DishRepository;
import com.backend.MyBackend.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishService{

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Dishes addDish(Long restaurantId,Dishes dish){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        dish.setRestaurant(restaurant);

        return dishRepository.save(dish);
    }
}
