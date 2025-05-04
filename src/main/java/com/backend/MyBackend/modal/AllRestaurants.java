package com.backend.MyBackend.modal;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name= "RESTAURANTS")
public class AllRestaurants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cuisine;
    private Boolean isOpen;
    private String phone;
    private BigDecimal rating;

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Constructors
    public AllRestaurants(String name, String cuisine, Boolean isOpen, String phone, BigDecimal rating) {
        this.name = name;
        this.cuisine = cuisine;
        this.isOpen = isOpen;
        this.rating = rating;
        this.phone = phone;
    }
    public AllRestaurants(){

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

}
