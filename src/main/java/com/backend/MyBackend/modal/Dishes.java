package com.backend.MyBackend.modal;

import jakarta.persistence.Embeddable;

@Embeddable
public class Dishes{
    private String dishName;
    private String description;
    private int price;

    public Dishes(String dishName,String description,int price){
        this.dishName = dishName;
        this.description = description;
        this.price = price;
    }

    public Dishes(){
    }

    public String getDishName(){
        return dishName;
    }

    public void setDishName(String dishName){
        this.dishName = dishName;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getPrice(){
        return price;
    }

    public void setPrice(int price){
        this.price = price;
    }
}
