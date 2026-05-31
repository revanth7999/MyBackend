package com.backend.MyBackend.cart.controller;

import com.backend.MyBackend.cart.dto.AddToCartRequest;
import com.backend.MyBackend.cart.entity.Cart;
import com.backend.MyBackend.cart.service.CartService;
import com.backend.MyBackend.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController{

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> saveCart(
            @Valid @RequestBody AddToCartRequest request){

        Cart savedCart = cartService.saveCart(request);
        return ResponseEntity.ok(
                new ApiResponse("Item added to cart",savedCart));
    }
}
