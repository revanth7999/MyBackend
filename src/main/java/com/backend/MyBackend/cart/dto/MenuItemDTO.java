package com.backend.MyBackend.cart.dto;

public class MenuItemDTO{
    private Long id;
    private String dishName;
    private String description;
    private Double price;
    private String dishImageUrl;

    // Getters
    public Long getId(){
        return id;
    }
    public String getDishName(){
        return dishName;
    }
    public String getDescription(){
        return description;
    }
    public Double getPrice(){
        return price;
    }
    public String getDishImageUrl(){
        return dishImageUrl;
    }

    // Setters
    public void setId(Long id){
        this.id = id;
    }
    public void setDishName(String dishName){
        this.dishName = dishName;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setPrice(Double price){
        this.price = price;
    }
    public void setDishImageUrl(String dishImageUrl){
        this.dishImageUrl = dishImageUrl;
    }

    // Constructors
    public MenuItemDTO(Long id,String dishName,String description,Double price){
        this.id = id;
        this.dishName = dishName;
        this.description = description;
        this.price = price;
    }

    public MenuItemDTO(){
    }
}
