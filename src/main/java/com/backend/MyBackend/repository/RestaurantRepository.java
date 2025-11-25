package com.backend.MyBackend.repository;

import com.backend.MyBackend.modal.AllRestaurants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<AllRestaurants, Long>{
    Page<AllRestaurants> findByNameContainingIgnoreCase(String name,Pageable pageable);

}
