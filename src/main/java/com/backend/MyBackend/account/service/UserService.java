package com.backend.MyBackend.account.service;

import com.backend.MyBackend.account.dto.CreateUserDto;
import com.backend.MyBackend.account.dto.LoginResponseDto;
import com.backend.MyBackend.account.dto.RolesDto;
import com.backend.MyBackend.account.dto.UserDto;
import com.backend.MyBackend.account.entity.LoginSession;
import com.backend.MyBackend.account.entity.Roles;
import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.account.repository.LoginSessionRepository;
import com.backend.MyBackend.account.repository.RolesRepository;
import com.backend.MyBackend.account.repository.UserRepository;
import com.backend.MyBackend.common.exception.UserInactiveException;
import com.backend.MyBackend.common.util.JwtUtil;
import com.backend.MyBackend.common.util.PasswordUtil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordUtil passwordUtil;
    private final LoginSessionRepository loginSessionRepository;

    public UserService(UserRepository userRepository,RolesRepository rolesRepository,PasswordUtil passwordUtil,
            LoginSessionRepository loginSessionRepository){
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordUtil = passwordUtil;
        this.loginSessionRepository = loginSessionRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    /**
     * Registers a new user by encrypting the password, setting role and active status, and saving the user to the
     * repository.
     */
    public UserDto register(CreateUserDto createUserDto){
        // 1. Instantiate a fresh Database Entity object
        User databaseUser = new User();
        databaseUser.setUsername(createUserDto.getUsername());
        databaseUser.setPassword(passwordUtil.passwordEncrypt(createUserDto.getPassword()));
        databaseUser.setRole(createUserDto.getRole() != null ? createUserDto.getRole() : "CUSTOMER");
        databaseUser.setIsActive(createUserDto.getIs_active() != null ? createUserDto.getIs_active() : true);
        databaseUser.setCreated_time_stamp(new Timestamp(System.currentTimeMillis()));
        databaseUser.setEmail("");
        databaseUser.setAddress("");
        User savedUser = userRepository.save(databaseUser);

        return new UserDto.UserDtoBuilder(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole(),
                savedUser.getIsActive())
                        .token("")
                        .email(savedUser.getEmail())
                        .address(savedUser.getAddress())
                        .build();
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
                                new UserDto.UserDtoBuilder(user.getId(),user.getUsername(),user.getRole(),
                                        user.getIsActive())
                                                .token("")
                                                .email(user.getEmail())
                                                .address(user.getAddress())
                                                .created_time_stamp(user.getCreated_time_stamp())
                                                .build()));
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
     * Authenticates a user by verifying the username and password. Returns a LoginRequest DTO if successful. Saves a
     * login session with IP address and device information.
     */
    public LoginResponseDto login(String username,String rawPassword,String deviceInfo){
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordUtil.passwordMatches(rawPassword,user.getPassword())){
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

        String accessToken = JwtUtil.generateToken(username,user.getRole());
        String refreshToken = JwtUtil.generateRefreshToken(username);

        // return new LoginResponseDto(user.getUsername(),user.getRole(),"","");
        return new LoginResponseDto.LoginResponseDtoBuilder(user.getUsername(),user.getRole())
                .accessToken(accessToken)
                .email(user.getEmail())
                .address(user.getAddress())
                .refreshToken(refreshToken)
                .build();
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

    public void logout(String username){
        User user = userRepository.findByUsername(username);

        Optional<LoginSession> optionalSession = loginSessionRepository
                .findTopByUserAndLogoutTimeIsNullOrderByLoginTimeDesc(user);

        optionalSession.ifPresent(session -> {
            session.setLogoutTime(new Timestamp(System.currentTimeMillis()));
            loginSessionRepository.save(session);
        });
    }

    /**
     * Fetches users with pagination.
     */
    public Page<User> getUsers(int page,int size,String search){
        log.info("Fetching users | page={}, size={}, search={}",page,size,search);
        Pageable pageable = PageRequest.of(page,size);

        if (search == null || search.trim().isEmpty()){
            return userRepository.findAll(pageable);
        }

        return userRepository.findByUsernameContainingIgnoreCase(search,pageable);
    }

}
