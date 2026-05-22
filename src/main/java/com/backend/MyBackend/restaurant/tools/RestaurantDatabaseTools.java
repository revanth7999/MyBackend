package com.backend.MyBackend.restaurant.tools;

import com.backend.MyBackend.restaurant.entity.Dishes;
import com.backend.MyBackend.restaurant.entity.Restaurant;
import com.backend.MyBackend.restaurant.repository.RestaurantRepository;
import dev.langchain4j.agent.tool.Tool;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class RestaurantDatabaseTools{

    private final RestaurantRepository restaurantRepository;

    public RestaurantDatabaseTools(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    @Tool("Fetches general information about a restaurant, including if it is open, its rating, and its phone number")
    public String getRestaurantInfo(String restaurantName){
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findByNameIgnoreCase(restaurantName);

        if (optionalRestaurant.isPresent()){
            Restaurant r = optionalRestaurant.get();
            String status = (r.getIsOpen() != null && r.getIsOpen()) ? "currently OPEN" : "currently CLOSED";

            return String.format(
                    "Restaurant '%s' serves %s cuisine. It is %s. Rating: %s stars. Phone: %s.",
                    r.getName(),r.getCuisine(),status,r.getRating(),r.getPhone());
        }
        return "I could not find a restaurant named " + restaurantName + " in our database.";
    }

    @Tool("Fetches the full menu (list of dishes) for a specific restaurant")
    public String getMenuForRestaurant(String restaurantName){
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findByNameIgnoreCase(restaurantName);

        if (optionalRestaurant.isPresent()){
            List<Dishes> dishes = optionalRestaurant.get().getDishes();

            if (dishes == null || dishes.isEmpty()){
                return "The restaurant '" + restaurantName + "' currently has no dishes listed on their menu.";
            }

            // Extract the names of the dishes into a comma-separated string
            String menuItems = dishes.stream()
                    .map(Dishes::getDishName) // Assuming Dishes has a getName() method
                    .collect(Collectors.joining(", "));

            return "The menu for " + restaurantName + " includes: " + menuItems;
        }
        return "I could not find a restaurant named " + restaurantName + " to check the menu.";
    }

    @Tool("Finds a list of all restaurants that serve a specific type of cuisine (e.g., Italian, Indian, Mexican)")
    public String findRestaurantsByCuisine(String cuisineType){
        List<Restaurant> restaurants = restaurantRepository.findByCuisineIgnoreCase(cuisineType);

        if (restaurants.isEmpty()){
            return "We currently have no " + cuisineType + " restaurants in our database.";
        }

        String restaurantNames = restaurants.stream()
                .map(Restaurant::getName)
                .collect(Collectors.joining(", "));

        return "Here are the " + cuisineType + " restaurants we have: " + restaurantNames;
    }
}
