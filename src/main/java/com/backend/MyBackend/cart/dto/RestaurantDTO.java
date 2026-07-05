package com.backend.MyBackend.cart.dto;

public class RestaurantDTO{
    private Long id;
    private String name;

    // Getters
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }

    // Setters
    public void setName(String name){
        this.name = name;
    }
    public void setId(Long id){
        this.id = id;
    }

    // Constructors
    public RestaurantDTO(Long id,String name){
        this.id = id;
        this.name = name;
    }

    public RestaurantDTO(){
    }
}
