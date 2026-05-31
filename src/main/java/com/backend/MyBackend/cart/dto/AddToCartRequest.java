package com.backend.MyBackend.cart.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class AddToCartRequest{

    @NotNull(message = "User ID must not be null")
    private Long userId;

    @NotNull(message = "Restaurant ID must not be null")
    private Long restaurantId;
    @NotEmpty(message = "Cart must have at least one item")
    private List<CartItemRequest> items;

    public Long getUserId(){
        return userId;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public Long getRestaurantId(){
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId){
        this.restaurantId = restaurantId;
    }

    public List<CartItemRequest> getItems(){
        return items;
    }

    public void setItems(List<CartItemRequest> items){
        this.items = items;
    }
}
