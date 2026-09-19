package com.backend.MyBackend.restaurant.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ReviewDto{
    private Long id;
    private Long userId;
    private String username;
    private Long restaurantId;
    private String restaurantName;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private Long restaurantId;
        private Integer rating;
        private String comment;
    }
}
