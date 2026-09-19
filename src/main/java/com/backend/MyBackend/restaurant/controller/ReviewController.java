package com.backend.MyBackend.restaurant.controller;

import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.account.service.UserService;
import com.backend.MyBackend.restaurant.dto.ReviewDto;
import com.backend.MyBackend.restaurant.service.ReviewService;
import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController{

    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ReviewDto.Request request,Principal principal){
        log.info("Received request to add review for restaurantId: {}",request.getRestaurantId());

        if (principal == null){
            log.warn("Authentication principal is null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication required");
        }

        String username = principal.getName();
        log.info("Authenticated user: {}",username);

        User user = userService.findByUsername(username);
        if (user == null){
            log.warn("User not found for username: {}",username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        try{
            ReviewDto review = reviewService.addReview(request,user.getId());
            return ResponseEntity.ok(review);
        } catch (Exception e){
            log.error("Error while adding review: {}",e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByRestaurant(@PathVariable Long restaurantId){
        return ResponseEntity.ok(reviewService.getReviewsByRestaurant(restaurantId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByUser(@PathVariable Long userId){
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id,Principal principal){
        if (principal == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = principal.getName();
        User user = userService.findByUsername(username);
        if (user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try{
            reviewService.deleteReview(id,user.getId());
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            log.error("Error while deleting review: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
