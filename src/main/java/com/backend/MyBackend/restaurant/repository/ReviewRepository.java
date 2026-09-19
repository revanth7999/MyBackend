package com.backend.MyBackend.restaurant.repository;

import com.backend.MyBackend.restaurant.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
    List<Review> findByRestaurantId(Long restaurantId);
    List<Review> findByUserId(Long userId);
}
