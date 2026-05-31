package com.backend.MyBackend.cart.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_item")
public class CartItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    private Long cartId;

    private Long menuItemId;

    private Integer quantity;

    private Double price;

    public Long getCartId(){
        return cartId;
    }

    public Long getMenuItemId(){
        return menuItemId;
    }

    public Integer getQuantity(){
        return quantity;
    }

    public Double getPrice(){
        return price;
    }

    public void setCartId(Long cartId){
        this.cartId = cartId;
    }

    public void setMenuItemId(Long menuItemId){
        this.menuItemId = menuItemId;
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public CartItem(Long cartId,Long menuItemId,Integer quantity,Double price){
        this.cartId = cartId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.price = price;
    }

    public CartItem(){
    }
}
