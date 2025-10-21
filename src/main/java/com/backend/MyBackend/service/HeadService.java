package com.backend.MyBackend.service;

import com.backend.MyBackend.dto.LoginResponseDto;
import com.backend.MyBackend.dto.RestaurantDto;
import com.backend.MyBackend.dto.RolesDto;
import com.backend.MyBackend.dto.UserDto;
import com.backend.MyBackend.exceptions.UserInactiveException;
import com.backend.MyBackend.modal.LoginSession;
import com.backend.MyBackend.modal.Roles;
import com.backend.MyBackend.modal.User;
import com.backend.MyBackend.repository.LoginSessionRepository;
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

    @Autowired
    private LoginSessionRepository loginSessionRepository;

    /**
     * Registers a new user by encrypting the password, setting role and active status, and saving the user to the
     * repository.
     */
    public UserDto register(User user){
        user.setPassword(utility.passwordEncrypt(user.getPassword()));
        user.setRole(user.getRole());
        user.setIsActive(user.getIsActive());
        user.setCreated_time_stamp(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
        return new UserDto(user.getId(),user.getUsername(),user.getRole(),user.getIsActive(),"");
    }

    public RolesDto adminRegisterUser(Roles role){
        role.setRoles(role.getRoles());
        rolesRepository.save(role);
        return new RolesDto(role.getRoles());
    }

    /**
     * Retrieves all users from the repository and maps them to UserDto objects.
     */
    public List<UserDto> getAllUsers(){
        List<UserDto> allUsers = new ArrayList<>();
        userRepository
                .findAll()
                .forEach(
                        user -> allUsers.add(
                                new UserDto(user.getId(),user.getUsername(),user.getRole(),user.getIsActive(),"")));
        return allUsers;
    }

    /**
     * Retrieves all roles from the repository and maps them to RolesDto objects.
     */
    public List<RolesDto> getAllRoles(){
        List<RolesDto> allRoles = new ArrayList<>();
        rolesRepository.findAll().forEach(role -> allRoles.add(new RolesDto(role.getRoles())));
        return allRoles;
    }

    /**
     * Retrieves all restaurants from the repository and maps them to RestaurantDto objects.
     */
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

    /**
     * Authenticates a user by verifying the username and password. Returns a LoginRequest DTO if successful. Saves a
     * login session with IP address and device information.
     */
    public LoginResponseDto login(String username,String rawPassword,String deviceInfo){
        User user = userRepository.findByUsername(username);
        if (user == null || !utility.passwordMatches(rawPassword,user.getPassword())){
            throw new RuntimeException("Invalid username or password");
        }

        // Check if user is active
        if (!user.getIsActive()){
            throw new UserInactiveException("Your account is deactivated. Please contact support.");
        }
        // Save login session
        String ipAddress = "10.20.1.0"; // Dummy Placeholder for IP address
        LoginSession session = new LoginSession(user,ipAddress,deviceInfo);
        loginSessionRepository.save(session);

        return new LoginResponseDto(user.getUsername(),user.getRole(),"","");
    }

    /**
     * Retrieves the role for a given username.
     */
    public String getRoleForUser(String username){
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user.getRole();
    }
}
