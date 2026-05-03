package com.backend.MyBackend.restaurant.repository;

import com.backend.MyBackend.restaurant.entity.Dishes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<Dishes, Long>{
}
