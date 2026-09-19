package com.backend.MyBackend.restaurant.service;

import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.account.repository.UserRepository;
import com.backend.MyBackend.restaurant.dto.ReviewDto;
import com.backend.MyBackend.restaurant.entity.Restaurant;
import com.backend.MyBackend.restaurant.entity.Review;
import com.backend.MyBackend.restaurant.repository.RestaurantRepository;
import com.backend.MyBackend.restaurant.repository.ReviewRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public ReviewDto addReview(ReviewDto.Request request,Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Review review = new Review();
        review.setUser(user);
        review.setRestaurant(restaurant);
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedAt(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);
        updateRestaurantRating(restaurant.getId());

        return mapToDto(savedReview);
    }

    @Override
    public List<ReviewDto> getReviewsByRestaurant(Long restaurantId){
        return reviewRepository.findByRestaurantId(restaurantId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> getReviewsByUser(Long userId){
        return reviewRepository.findByUserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId,Long userId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (!review.getUser().getId().equals(userId)){
            throw new RuntimeException("Unauthorized to delete this review");
        }
        Long restaurantId = review.getRestaurant().getId();
        reviewRepository.delete(review);
        updateRestaurantRating(restaurantId);
    }

    private void updateRestaurantRating(Long restaurantId){
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);
        if (reviews.isEmpty()){
            Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
            restaurant.setRating(BigDecimal.ZERO);
            restaurantRepository.save(restaurant);
            return;
        }

        double average = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        restaurant.setRating(BigDecimal.valueOf(average).setScale(1,RoundingMode.HALF_UP));
        restaurantRepository.save(restaurant);
    }

    private ReviewDto mapToDto(Review review){
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setUserId(review.getUser().getId());
        dto.setUsername(review.getUser().getUsername());
        dto.setRestaurantId(review.getRestaurant().getId());
        dto.setRestaurantName(review.getRestaurant().getName());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }
}
