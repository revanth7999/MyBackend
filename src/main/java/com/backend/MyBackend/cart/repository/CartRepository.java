package com.backend.MyBackend.cart.repository;

import com.backend.MyBackend.cart.entity.Cart;
import com.backend.MyBackend.cart.enums.CartStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>{

    Optional<Cart> findByUserIdAndStatus(Long userId,CartStatus cartStatus);
}
