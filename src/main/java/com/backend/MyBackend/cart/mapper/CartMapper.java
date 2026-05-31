package com.backend.MyBackend.cart.mapper;

import com.backend.MyBackend.cart.dto.AddToCartRequest;
import com.backend.MyBackend.cart.dto.CartItemRequest;
import com.backend.MyBackend.cart.entity.Cart;
import com.backend.MyBackend.cart.entity.CartItem;
import com.backend.MyBackend.cart.enums.CartStatus;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CartMapper{
    public Cart buildCart(AddToCartRequest request){
        Cart cart = new Cart();
        cart.setUserId(request.getUserId());
        cart.setStatus(CartStatus.ACTIVE);
        return cart;
    }

    public List<CartItem> buildCartItems(List<CartItemRequest> items,Long cartId){
        return items.stream()
                .map(item -> {
                    CartItem cartItem = new CartItem();
                    cartItem.setCartId(cartId);
                    cartItem.setMenuItemId(item.getMenuItemId());
                    cartItem.setQuantity(item.getQuantity());
                    cartItem.setPrice(item.getPrice());
                    return cartItem;
                })
                .collect(Collectors.toList());
    }
}
