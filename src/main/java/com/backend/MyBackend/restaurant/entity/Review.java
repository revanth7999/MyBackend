package com.backend.MyBackend.restaurant.entity;

import com.backend.MyBackend.account.entity.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "REVIEWS")
public class Review{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private LocalDateTime createdAt;

    public Review(){
    }

    public Review(User user,Restaurant restaurant,Integer rating,String comment,LocalDateTime createdAt){
        this.user = user;
        this.restaurant = restaurant;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }

    public Restaurant getRestaurant(){
        return restaurant;
    }
    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
    }

    public Integer getRating(){
        return rating;
    }
    public void setRating(Integer rating){
        this.rating = rating;
    }

    public String getComment(){
        return comment;
    }
    public void setComment(String comment){
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }
}
