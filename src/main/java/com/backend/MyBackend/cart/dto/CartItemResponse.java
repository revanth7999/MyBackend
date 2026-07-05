package com.backend.MyBackend.cart.dto;

public class CartItemResponse{
    private Long cartId;
    private Integer quantity;
    private MenuItemDTO menuItem;
    private RestaurantDTO restaurant;

    // Getters
    public Long getCartId(){
        return cartId;
    }
    public Integer getQuantity(){
        return quantity;
    }
    public MenuItemDTO getMenuItem(){
        return menuItem;
    }
    public RestaurantDTO getRestaurant(){
        return restaurant;
    }

    // Setters
    public void setCartId(Long cartId){
        this.cartId = cartId;
    }
    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }
    public void setMenuItem(MenuItemDTO menuItem){
        this.menuItem = menuItem;
    }
    public void setRestaurant(RestaurantDTO restaurant){
        this.restaurant = restaurant;
    }

    // Constructors
    public CartItemResponse(Long cartId,Integer quantity,MenuItemDTO menuItem,RestaurantDTO restaurant){
        this.cartId = cartId;
        this.quantity = quantity;
        this.menuItem = menuItem;
        this.restaurant = restaurant;
    }

    public CartItemResponse(){
    }
}
