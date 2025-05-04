package com.backend.MyBackend.repository;

import com.backend.MyBackend.modal.AllRestaurants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<AllRestaurants, Long> {
}
