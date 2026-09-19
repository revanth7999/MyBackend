package com.backend.MyBackend.restaurant.service;

import com.backend.MyBackend.restaurant.dto.ReviewDto;
import java.util.List;

public interface ReviewService{
    ReviewDto addReview(ReviewDto.Request request,Long userId);
    List<ReviewDto> getReviewsByRestaurant(Long restaurantId);
    List<ReviewDto> getReviewsByUser(Long userId);
    void deleteReview(Long reviewId,Long userId);
}
