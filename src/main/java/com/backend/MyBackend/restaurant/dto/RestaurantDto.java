package com.backend.MyBackend.restaurant.dto;

import com.backend.MyBackend.restaurant.entity.Address;
import com.backend.MyBackend.restaurant.entity.Dishes;
import java.math.BigDecimal;
import java.util.List;

public class RestaurantDto{

    private String name;
    private String cuisine;
    private Boolean isOpen;
    private String phone;
    private BigDecimal rating;
    private Address address;
    private List<Dishes> dishes;

    // Getters
    public String getName(){
        return name;
    }
    public String getCuisine(){
        return cuisine;
    }
    public Boolean getOpen(){
        return isOpen;
    }
    public String getPhone(){
        return phone;
    }
    public BigDecimal getRating(){
        return rating;
    }
    public Address getAddress(){
        return address;
    }
    public List<Dishes> getDishes(){
        return dishes;
    }

    // Setters
    public void setName(String name){
        this.name = name;
    }
    public void setCuisine(String cuisine){
        this.cuisine = cuisine;
    }
    public void setOpen(Boolean open){
        isOpen = open;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public void setRating(BigDecimal rating){
        this.rating = rating;
    }
    public void setAddress(Address address){
        this.address = address;
    }
    public void setDishes(List<Dishes> dishes){
        this.dishes = dishes;
    }

    // No-Args constructor
    public RestaurantDto(){
    }

    /**
     * RestaurantDto Constructor with Builder Design pattern.
     */
    public RestaurantDto(BuilderRestaurantDto builder){
        this.name = builder.name;
        this.cuisine = builder.cuisine;
        this.isOpen = builder.isOpen;
        this.phone = builder.phone;
        this.rating = builder.rating;
        this.address = builder.address;
        this.dishes = builder.dishes;
    }

    /**
     * Builder Design pattern
     */
    public static class BuilderRestaurantDto{
        private final String name;
        private final String cuisine;
        private Boolean isOpen;
        private String phone;
        private BigDecimal rating;
        private Address address;
        private List<Dishes> dishes;

        public BuilderRestaurantDto(String name,String cuisine){
            this.name = name;
            this.cuisine = cuisine;
        }

        public BuilderRestaurantDto isOpen(Boolean isOpen){
            this.isOpen = isOpen;
            return this;
        }

        public BuilderRestaurantDto phone(String phone){
            this.phone = phone;
            return this;
        }

        public BuilderRestaurantDto rating(BigDecimal rating){
            this.rating = rating;
            return this;
        }

        public BuilderRestaurantDto address(Address address){
            this.address = address;
            return this;
        }

        public BuilderRestaurantDto dishes(List<Dishes> dishes){
            this.dishes = dishes;
            return this;
        }

        public RestaurantDto build(){
            return new RestaurantDto(this);
        }
    }

    // // Generic constructor
    // public RestaurantDto(
    // String name,
    // String cuisine,
    // Boolean isOpen,
    // String phone,
    // BigDecimal rating,
    // Address address,
    // List<Dishes> dishes){
    // this.name = name;
    // this.cuisine = cuisine;
    // this.isOpen = isOpen;
    // this.phone = phone;
    // this.rating = rating;
    // this.address = address;
    // this.dishes = dishes;
    // }
}
