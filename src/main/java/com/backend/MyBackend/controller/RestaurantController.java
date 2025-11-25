package com.backend.MyBackend.controller;

import com.backend.MyBackend.dto.ApiResponse;
import com.backend.MyBackend.service.HeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev/restaurant")
@CrossOrigin(origins = "*")
public class RestaurantController{

    @Autowired
    private HeadService headService;

    @GetMapping("/allRestaurants")
    public ResponseEntity<ApiResponse> getallRestaurants(){
        return ResponseEntity.ok(new ApiResponse("Rendered Successfully",headService.getallRestaurants()));
    }

    /**
     * Endpoint to fetch restaurants with pagination.
     */
    @GetMapping("/restaurants")
    public ResponseEntity<ApiResponse> getRestaurantsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(
                new ApiResponse(
                        "Restaurants fetched successfully",
                        headService.getRestaurantsWithPagination(page,size)));
    }

    @GetMapping("/restaurantssearch")
    public ResponseEntity<ApiResponse> getRestaurantsWithPaginationSearch(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "10") String search){
        return ResponseEntity.ok(
                new ApiResponse(
                        "Restaurants fetched successfully",
                        headService.getRestaurantsWithPaginationSearch(page,size,search)));
    }
}
