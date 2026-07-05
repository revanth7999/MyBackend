package com.backend.MyBackend.cart.service;

import com.backend.MyBackend.cart.dto.AddToCartRequest;
import com.backend.MyBackend.cart.dto.CartItemResponse;
import com.backend.MyBackend.cart.dto.MenuItemDTO;
import com.backend.MyBackend.cart.dto.RestaurantDTO;
import com.backend.MyBackend.cart.entity.Cart;
import com.backend.MyBackend.cart.entity.CartItem;
import com.backend.MyBackend.cart.enums.CartStatus;
import com.backend.MyBackend.cart.exception.ActiveCartNotFoundException;
import com.backend.MyBackend.cart.exception.CartAlreadyExistsException;
import com.backend.MyBackend.cart.mapper.CartMapper;
import com.backend.MyBackend.cart.repository.CartItemRepository;
import com.backend.MyBackend.cart.repository.CartRepository;
import com.backend.MyBackend.restaurant.entity.Dishes;
import com.backend.MyBackend.restaurant.entity.Restaurant;
import com.backend.MyBackend.restaurant.repository.DishRepository;
import com.backend.MyBackend.restaurant.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CartService{

    private static final Logger log = LoggerFactory.getLogger(CartService.class);
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CartItemRepository cartItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    public CartService(CartRepository cartRepository,CartItemRepository cartItemRepository,CartMapper cartMapper,
            RestaurantRepository restaurantRepository,DishRepository dishRepository){
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.cartItemRepository = cartItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;

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

    /**
     * Fetches the active cart for a user and returns its items. If no active cart is found, an exception is thrown.
     *
     * @param userId
     *            The ID of the user whose cart items are to be fetched.
     * @return A list of CartItem objects representing the items in the user's active cart.
     */
    public List<CartItemResponse> getCart(Long userId){

        log.info("Fetching cart for userId={}",userId);
        Optional<Cart> cartOpt = cartRepository.findByUserIdAndStatus(userId,CartStatus.ACTIVE);

        if (cartOpt.isPresent()){
            Cart cart = cartOpt.get();
            log.info("Active cart found for userId={}, cartId={}",userId,cart.getCartId());
            List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());
            Restaurant restaurant = restaurantRepository.findById(cart.getRestaurantId())
                    .orElseThrow(() -> new RuntimeException("Restaurant not found"));
            List<CartItemResponse> cartItemResponses = cartItems.stream()
                    .map(item -> {
                        Dishes dish = dishRepository.findById(item.getMenuItemId())
                                .orElseThrow(() -> new RuntimeException("Dish not found"));
                        CartItemResponse dto = new CartItemResponse();
                        dto.setCartId(item.getCartId());
                        dto.setQuantity(item.getQuantity());

                        MenuItemDTO menuItemDTO = new MenuItemDTO();
                        menuItemDTO.setId(item.getMenuItemId());
                        menuItemDTO.setDishName(dish.getDishName());
                        menuItemDTO.setDescription(dish.getDescription());
                        menuItemDTO.setPrice(item.getPrice());
                        menuItemDTO.setDishImageUrl("https://picsum.photos/80?1");

                        RestaurantDTO restaurantDTO = new RestaurantDTO();
                        restaurantDTO.setId(cart.getRestaurantId());
                        restaurantDTO.setName(restaurant.getName());

                        dto.setMenuItem(menuItemDTO);
                        dto.setRestaurant(restaurantDTO);

                        return dto;
                    })
                    .toList();

            return cartItemResponses;
        } else{
            throw new ActiveCartNotFoundException("No active cart found for this user");
        }

    }

}
