package com.backend.MyBackend.modal;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RESTAURANTS")
public class AllRestaurants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String cuisine;
    private Boolean isOpen;
    private String phone;
    private BigDecimal rating;

    @Embedded
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @ElementCollection
    @CollectionTable(name = "restaurant_dishes", joinColumns = @JoinColumn(name = "restaurant_id"))
    private List<Dishes> dishes = new ArrayList<>();

    public List<Dishes> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dishes> dishes) {
        this.dishes = dishes;
    }

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
    public AllRestaurants(
            String name,
            String cuisine,
            Boolean isOpen,
            String phone,
            BigDecimal rating,
            Address address,
            List<Dishes> dishes) {
        this.name = name;
        this.cuisine = cuisine;
        this.isOpen = isOpen;
        this.rating = rating;
        this.phone = phone;
        this.address = address;
        this.dishes = dishes;
    }

    public AllRestaurants() {}

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
