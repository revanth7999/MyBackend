package com.backend.MyBackend.dto;

import com.backend.MyBackend.modal.Address;
import com.backend.MyBackend.modal.Dishes;

import java.math.BigDecimal;
import java.util.List;

public class RestaurantDto {

    private String name;
    private String cuisine;
    private Boolean isOpen;
    private String phone;
    private BigDecimal rating;
    private Address address;
    private List<Dishes> dishes;

    public List<Dishes> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dishes> dishes) {
        this.dishes = dishes;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public RestaurantDto() {}

    public RestaurantDto(
            String name, String cuisine, Boolean isOpen, String phone, BigDecimal rating, Address address, List<Dishes> dishes) {
        this.name = name;
        this.cuisine = cuisine;
        this.isOpen = isOpen;
        this.phone = phone;
        this.rating = rating;
        this.address = address;
        this.dishes = dishes;
    }
}
