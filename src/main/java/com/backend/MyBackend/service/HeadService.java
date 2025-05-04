package com.backend.MyBackend.service;

import com.backend.MyBackend.dto.RestaurantDto;
import com.backend.MyBackend.dto.UserDto;
import com.backend.MyBackend.modal.User;
import com.backend.MyBackend.repository.RestaurantRepository;
import com.backend.MyBackend.repository.UserRepository;
import com.backend.MyBackend.utils.Utility;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeadService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private Utility utility;

    public UserDto register(User user) {
        user.setPassword(utility.passwordEncrypt(user.getPassword()));
        user.setRole(user.getRole());
        user.setIsActive(user.getIsActive());
        user.setCreated_time_stamp(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
        return new UserDto(user.getUsername(), user.getRole());
    }

    public List<UserDto> getAllUsers() {
        List<UserDto> allUsers = new ArrayList<>();
        userRepository.findAll().forEach(user -> allUsers.add(new UserDto(user.getUsername(), user.getRole())));
        return allUsers;
    }

    public List<RestaurantDto> getallRestaurants() {
        List<RestaurantDto> allRestaurants = new ArrayList<>();
        restaurantRepository
                .findAll()
                .forEach(res -> allRestaurants.add(new RestaurantDto(
                        res.getName(), res.getCuisine(), res.getIsOpen(), res.getPhone(), res.getRating())));
        return allRestaurants;
    }
}
