package com.backend.MyBackend.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RESTAURANTS")
public class Restaurant{

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

    // @ElementCollection
    // @CollectionTable(name = "restaurant_dishes", joinColumns = @JoinColumn(name = "restaurant_id"))
    // private List<Dishes> dishes = new ArrayList<>();
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Dishes> dishes = new ArrayList<>();;

    // Getters
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getCuisine(){
        return cuisine;
    }
    public Boolean getIsOpen(){
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
    public void setId(Long id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCuisine(String cuisine){
        this.cuisine = cuisine;
    }
    public void setIsOpen(Boolean isOpen){
        this.isOpen = isOpen;
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

    // Constructors
    public Restaurant(
            String name,
            String cuisine,
            Boolean isOpen,
            String phone,
            BigDecimal rating,
            Address address,
            List<Dishes> dishes){
        this.name = name;
        this.cuisine = cuisine;
        this.isOpen = isOpen;
        this.rating = rating;
        this.phone = phone;
        this.address = address;
        this.dishes = dishes;
    }

    public Restaurant(){
    }

}
