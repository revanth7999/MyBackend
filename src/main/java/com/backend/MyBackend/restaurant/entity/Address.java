package com.backend.MyBackend.restaurant.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address{
    private String street;
    private String city;
    private String state;
    private String zipcode;

    // Getters
    public String getStreet(){
        return street;
    }
    public String getCity(){
        return city;
    }
    public String getState(){
        return state;
    }
    public String getZipcode(){
        return zipcode;
    }

    // Setters
    public void setStreet(String street){
        this.street = street;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setState(String state){
        this.state = state;
    }
    public void setZipcode(String zipcode){
        this.zipcode = zipcode;
    }

    // Constructor
    public Address(String street,String city,String state,String zipcode){
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    // No-Arg Constructors
    public Address(){
    }

}
