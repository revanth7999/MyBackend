package com.backend.MyBackend.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dishes")
public class Dishes{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dishName;
    private String description;
    private int price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference
    private Restaurant restaurant;

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
    public int getPrice(){
        return price;
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
    public void setPrice(int price){
        this.price = price;
    }

    // Constructor
    public Dishes(String dishName,String description,int price){
        this.dishName = dishName;
        this.description = description;
        this.price = price;
    }

    // No-Arg Constructors
    public Dishes(){
    }

    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
    }
}
