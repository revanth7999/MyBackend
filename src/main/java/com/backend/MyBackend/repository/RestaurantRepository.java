package com.backend.MyBackend.repository;

import com.backend.MyBackend.modal.AllRestaurants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<AllRestaurants, Long>{

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
    Page<AllRestaurants> findByNameContainingIgnoreCase(String name,Pageable pageable);

}
