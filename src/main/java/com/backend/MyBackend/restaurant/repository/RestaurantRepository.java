package com.backend.MyBackend.restaurant.repository;

import com.backend.MyBackend.restaurant.entity.Restaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

    /**
     * Finds restaurants with names containing the specified string, ignoring case sensitivity, and returns them in a
     * paginated format.
     *
     * @param name
     *            The string to search for within restaurant names.
     * @param pageable
     *            The pagination information.
     * @return A page of restaurants matching the search criteria.
     */
    Page<Restaurant> findByNameContainingIgnoreCase(String name,Pageable pageable);

    // Custom query to find a restaurant by its exact name
    Optional<Restaurant> findByNameIgnoreCase(String name);

    // Custom query to find all restaurants of a specific type (e.g., "Italian")
    List<Restaurant> findByCuisineIgnoreCase(String cuisine);

}
