package com.backend.MyBackend.dto;

import java.math.BigDecimal;

public class RestaurantDto {

    private String name;
    private String cuisine;
    private Boolean isOpen;
    private String phone;
    private BigDecimal rating;

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

    public RestaurantDto(){
    }

    public RestaurantDto(String name, String cuisine, Boolean isOpen, String phone, BigDecimal rating){
        this.name = name;
        this.cuisine = cuisine;
        this.isOpen = isOpen;
        this.phone = phone;
        this.rating = rating;
    }


}
