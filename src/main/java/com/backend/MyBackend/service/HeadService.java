package com.backend.MyBackend.service;

import com.backend.MyBackend.dto.LoginRequest;
import com.backend.MyBackend.dto.RestaurantDto;
import com.backend.MyBackend.dto.RolesDto;
import com.backend.MyBackend.dto.UserDto;
import com.backend.MyBackend.modal.Roles;
import com.backend.MyBackend.modal.User;
import com.backend.MyBackend.repository.RestaurantRepository;
import com.backend.MyBackend.repository.RolesRepository;
import com.backend.MyBackend.repository.UserRepository;
import com.backend.MyBackend.utils.Utility;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HeadService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private Utility utility;

    public UserDto register(User user){
        user.setPassword(utility.passwordEncrypt(user.getPassword()));
        user.setRole(user.getRole());
        user.setis_active(user.getis_active());
        user.setCreated_time_stamp(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
        return new UserDto(user.getUsername(),user.getRole(),user.getis_active(),"");
    }

    public RolesDto adminRegisterUser(Roles role){
        role.setRoles(role.getRoles());
        rolesRepository.save(role);
        return new RolesDto(role.getRoles());
    }

    public List<UserDto> getAllUsers(){
        List<UserDto> allUsers = new ArrayList<>();
        userRepository
                .findAll()
                .forEach(
                        user -> allUsers.add(new UserDto(user.getUsername(),user.getRole(),user.getis_active(),"")));
        return allUsers;
    }

    public List<RolesDto> getAllRoles(){
        List<RolesDto> allRoles = new ArrayList<>();
        rolesRepository.findAll().forEach(role -> allRoles.add(new RolesDto(role.getRoles())));
        return allRoles;
    }

    public List<RestaurantDto> getallRestaurants(){
        List<RestaurantDto> allRestaurants = new ArrayList<>();
        restaurantRepository
                .findAll()
                .forEach(res -> allRestaurants.add(new RestaurantDto(
                        res.getName(),
                        res.getCuisine(),
                        res.getIsOpen(),
                        res.getPhone(),
                        res.getRating(),
                        res.getAddress(),
                        res.getDishes())));
        return allRestaurants;
    }

    public LoginRequest login(String username,String rawPassword){
        User user = userRepository.findByUsername(username);
        if (user == null || !utility.passwordMatches(rawPassword,user.getPassword())){
            throw new RuntimeException("Invalid username or password");
        }
        return new LoginRequest(user.getUsername(),user.getRole(),"","");
    }

    public String getRoleForUser(String username){
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user.getRole();
    }
}
