package com.backend.MyBackend.cart.repository;

import com.backend.MyBackend.cart.entity.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    List<CartItem> findByCartId(Long cartId);

}
