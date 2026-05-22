package com.backend.MyBackend.restaurant.controller;

import com.backend.MyBackend.common.dto.ApiResponse;
import com.backend.MyBackend.restaurant.agent.RestaurantAgent;
import com.backend.MyBackend.restaurant.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dev/restaurants")
@CrossOrigin(origins = "*")
public class RestaurantController{

    private final RestaurantService restaurantService;
    private final RestaurantAgent restaurantAgent;

    public RestaurantController(RestaurantService restaurantService,RestaurantAgent restaurantAgent){
        this.restaurantService = restaurantService;
        this.restaurantAgent = restaurantAgent;
    }

    /**
     * Endpoint to fetch restaurants with pagination and search functionality.
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

    /**
     * Endpoint to converse with the Restaurant AI Agent. It queries the database using tools to answer natural language
     * questions.
     *
     * @param userPrompt
     *            The natural language question from the user.
     * @return A ResponseEntity containing the AI's response inside your standard ApiResponse wrapper.
     */
    @PostMapping("/ask")
    public ResponseEntity<ApiResponse> askAgent(@RequestBody String userPrompt){
        // Send the message to the AI, which automatically interacts with your DB tools
        String aiResponse = restaurantAgent.chat(userPrompt);

        return ResponseEntity.ok(
                new ApiResponse(
                        "AI Agent responded successfully",
                        aiResponse));
    }
}
