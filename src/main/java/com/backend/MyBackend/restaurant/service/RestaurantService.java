package com.backend.MyBackend.restaurant.service;

import com.backend.MyBackend.restaurant.dto.RestaurantDto;
import com.backend.MyBackend.restaurant.entity.Restaurant;
import com.backend.MyBackend.restaurant.mapper.RestaurantMapper;
import com.backend.MyBackend.restaurant.repository.RestaurantRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService{

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantService(RestaurantRepository restaurantRepository,RestaurantMapper restaurantMapper){
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

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

    public RestaurantDto createRestaurant(RestaurantDto restaurantDto){
        log.info("Creating restaurant");
        Restaurant restaurant = restaurantMapper.toEntity(restaurantDto);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return restaurantMapper.toDto(savedRestaurant);
    }

    public Optional<Restaurant> getRestaurantById(Long id){
        log.info("Fetching restaurant");
        return restaurantRepository.findById(id);
    }
}
