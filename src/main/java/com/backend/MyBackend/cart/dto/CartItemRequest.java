package com.backend.MyBackend.cart.dto;

public class CartItemRequest{

    private Long menuItemId;
    private Integer quantity;
    private Double price;

    public Long getMenuItemId(){
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId){
        this.menuItemId = menuItemId;
    }

    public Integer getQuantity(){
        return quantity;
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    public Double getPrice(){
        return price;
    }

    public void setPrice(Double price){
        this.price = price;
    }
}
