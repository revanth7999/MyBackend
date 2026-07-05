package com.backend.MyBackend.cart.entity;

import com.backend.MyBackend.cart.enums.CartStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "cart")
@EntityListeners(AuditingEntityListener.class)
public class Cart{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private CartStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    // Getters
    public Long getCartId(){
        return cartId;
    }
    public Long getUserId(){
        return userId;
    }
    public CartStatus getStatus(){
        return status;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }
    public Long getRestaurantId(){
        return restaurantId;
    }

    // Setters
    public void setUserId(Long userId){
        this.userId = userId;
    }
    public void setStatus(CartStatus status){
        this.status = status;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
    }
    public void setRestaurantId(Long restaurantId){
        this.restaurantId = restaurantId;
    }

    // Constructors
    public Cart(Long userId,CartStatus status,LocalDateTime createdAt,LocalDateTime updatedAt,Long restaurantId){
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.restaurantId = restaurantId;
    }

    public Cart(){
    }
}
