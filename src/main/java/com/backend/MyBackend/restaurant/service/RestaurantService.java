package com.backend.MyBackend.restaurant.service;

import com.backend.MyBackend.restaurant.entity.Restaurant;
import com.backend.MyBackend.restaurant.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;

    private static final Logger log = LoggerFactory.getLogger(RestaurantService.class);

    /**
     * Fetches restaurants with pagination.
     */
    public Page<Restaurant> getRestaurants(int page,int size,String search){
        log.info("Fetching restaurants | page={}, size={}, search={}",page,size,search);
        Pageable pageable = PageRequest.of(page,size);

        if (search == null || search.trim().isEmpty()){
            return restaurantRepository.findAll(pageable);
        }

        return restaurantRepository.findByNameContainingIgnoreCase(search,pageable);
    }
}
