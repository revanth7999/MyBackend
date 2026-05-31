package com.backend.MyBackend.cart.service;

import com.backend.MyBackend.cart.dto.AddToCartRequest;
import com.backend.MyBackend.cart.entity.Cart;
import com.backend.MyBackend.cart.entity.CartItem;
import com.backend.MyBackend.cart.enums.CartStatus;
import com.backend.MyBackend.cart.exception.CartAlreadyExistsException;
import com.backend.MyBackend.cart.mapper.CartMapper;
import com.backend.MyBackend.cart.repository.CartItemRepository;
import com.backend.MyBackend.cart.repository.CartRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CartService{

    private static final Logger log = LoggerFactory.getLogger(CartService.class);
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository,CartItemRepository cartItemRepository,CartMapper cartMapper){
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public Cart saveCart(AddToCartRequest request){
        cartRepository.findByUserIdAndStatus(request.getUserId(),CartStatus.ACTIVE)
                .ifPresent(c -> {
                    throw new CartAlreadyExistsException("Active cart already exists");
                });

        Cart saved = cartRepository.save(cartMapper.buildCart(request));

        List<CartItem> cartItems = cartMapper.buildCartItems(request.getItems(),saved.getCartId());
        cartItemRepository.saveAll(cartItems);

        log.info("Cart saved successfully for userId={}, cartId={}",request.getUserId(),saved.getCartId());
        return saved;
    }

}
