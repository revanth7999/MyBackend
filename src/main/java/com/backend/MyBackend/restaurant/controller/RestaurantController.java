package com.backend.MyBackend.restaurant.controller;

import com.backend.MyBackend.common.dto.ApiResponse;
import com.backend.MyBackend.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev/restaurants")
@CrossOrigin(origins = "*")
public class RestaurantController{

    @Autowired
    private RestaurantService restaurantService;

    /**
     * Endpoint to fetch restaurants with pagination and search functionality.
     *
     * @param page
     *            The page number to retrieve (default is 0).
     * @param size
     *            The number of items per page (default is 10).
     * @param search
     *            The search string to filter restaurant names (default is empty string).
     * @return A ResponseEntity containing an ApiResponse with the fetched restaurants.
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search){

        return ResponseEntity.ok(
                new ApiResponse(
                        "Restaurants fetched successfully",
                        restaurantService.getRestaurants(page,size,search)));
    }
}
