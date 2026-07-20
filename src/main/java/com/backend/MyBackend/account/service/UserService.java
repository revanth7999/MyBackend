package com.backend.MyBackend.account.service;

import com.backend.MyBackend.account.dto.ChangePasswordRequestDto;
import com.backend.MyBackend.account.dto.CreateUserDto;
import com.backend.MyBackend.account.dto.LoginResponseDto;
import com.backend.MyBackend.account.dto.RegisterUserResponseDto;
import com.backend.MyBackend.account.dto.RolesDto;
import com.backend.MyBackend.account.dto.UserDto;
import com.backend.MyBackend.account.entity.LoginSession;
import com.backend.MyBackend.account.entity.Roles;
import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.account.repository.LoginSessionRepository;
import com.backend.MyBackend.account.repository.RolesRepository;
import com.backend.MyBackend.account.repository.UserRepository;
import com.backend.MyBackend.common.constants.Constants;
import com.backend.MyBackend.common.dto.MetaDto;
import com.backend.MyBackend.common.dto.TokenDto;
import com.backend.MyBackend.common.dto.UserDetailsDto;
import com.backend.MyBackend.common.exception.UserInactiveException;
import com.backend.MyBackend.common.util.JwtUtil;
import com.backend.MyBackend.common.util.PasswordUtil;
import com.backend.MyBackend.exception.InvalidExistingPasswordException;
import com.backend.MyBackend.exception.InvalidPasswordException;
import com.backend.MyBackend.exception.UserNameAlreadyTaken;
import com.backend.MyBackend.exception.UserNotFoundException;
import com.backend.MyBackend.notification.service.NotificationService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    @Value("${app.environment}")
    private String environment;

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordUtil passwordUtil;
    private final LoginSessionRepository loginSessionRepository;
    private final NotificationService notificationService;

    public UserService(UserRepository userRepository,RolesRepository rolesRepository,PasswordUtil passwordUtil,
            LoginSessionRepository loginSessionRepository,NotificationService notificationService){
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordUtil = passwordUtil;
        this.loginSessionRepository = loginSessionRepository;
        this.notificationService = notificationService;
    }

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    /**
     * Registers a new user by encrypting the password, setting role and active status, and saving the user to the
     * repository.
     */
    public RegisterUserResponseDto register(CreateUserDto createUserDto){
        boolean alreadyTaken = isUsernameAvailable(createUserDto.getUsername());
        if (alreadyTaken){
            throw new UserNameAlreadyTaken(Constants.USERNAME_UNAVAILABLE);
        }
        User databaseUser = new User();
        databaseUser.setUsername(createUserDto.getUsername());
        databaseUser.setPassword(passwordUtil.passwordEncrypt(createUserDto.getPassword()));
        databaseUser.setRole(createUserDto.getRole() != null ? createUserDto.getRole() : "CUSTOMER");
        databaseUser.setIsActive(createUserDto.getIs_active() != null ? createUserDto.getIs_active() : true);
        databaseUser.setCreated_time_stamp(new Timestamp(System.currentTimeMillis()));
        databaseUser.setEmail(createUserDto.getEmail());
        databaseUser.setAddress("");
        User savedUser = userRepository.save(databaseUser);

        notificationService.createWelcomeNotification(savedUser);
        notificationService.createEmailVerificationNotification(savedUser);

        String accessToken = JwtUtil.generateToken(savedUser.getUsername(),savedUser.getRole());
        String refreshToken = JwtUtil.generateRefreshToken(savedUser.getUsername());

        return new RegisterUserResponseDto.RegisterUserResponseDtoBuilder()
                .user(new UserDetailsDto.UserDtoBuilder(
                        savedUser.getId(),
                        savedUser.getUsername(),
                        savedUser.getRole())
                                .email(savedUser.getEmail())
                                .emailVerified(savedUser.getIsEmailVerified())
                                .address(savedUser.getAddress())
                                .build())
                .tokens(new TokenDto.TokenDtoBuilder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build())
                .meta(new MetaDto.MetaDtoBuilder()
                        .environment(environment)
                        .build())
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

        notificationService.ensureDefaultNotifications(user);

        return new LoginResponseDto.LoginResponseDtoBuilder()
                .user(new UserDetailsDto.UserDtoBuilder(
                        user.getId(),
                        user.getUsername(),
                        user.getRole())
                                .email(user.getEmail())
                                .emailVerified(user.getIsEmailVerified())
                                .address(user.getAddress())
                                .build())
                .tokens(new TokenDto.TokenDtoBuilder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build())
                .meta(new MetaDto.MetaDtoBuilder()
                        .environment(environment)
                        .build())
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

    public Boolean isUsernameAvailable(String username){
        log.info("Fetching any matching username for={}",username);
        return userRepository.existsByUsername(username);
    }

    /**
     * Updates the password for the specified user.
     *
     * <p>
     * This method performs the following validations before updating the password:
     * <ul>
     * <li>Verifies that the user exists.</li>
     * <li>Validates that the provided current (old) password matches the stored password.</li>
     * <li>Ensures that the new password is different from the current password.</li>
     * </ul>
     *
     * <p>
     * If all validations pass, the new password is encrypted and persisted to the database.
     *
     * @param id
     *            the unique identifier of the user whose password is to be updated
     * @param dto
     *            the request containing the current password and the new password
     * @throws UserNotFoundException
     *             if no user exists with the specified ID
     * @throws InvalidExistingPasswordException
     *             if the provided current password is incorrect
     * @throws InvalidPasswordException
     *             if the new password is the same as the current password
     */
    public void updatePassword(Long id,ChangePasswordRequestDto dto){

        log.info("Fetching user {}",id);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User {} not found - Cannot update password",id);
                    return new UserNotFoundException("User not found");
                });

        log.info("Found user {} - Validating password",id);

        // Verify old password
        if (!passwordUtil.passwordMatches(dto.getOldPassword(),existingUser.getPassword())){
            throw new InvalidExistingPasswordException("Invalid existing password");
        }

        // Ensure new password is different
        if (passwordUtil.passwordMatches(dto.getNewPassword(),existingUser.getPassword())){
            throw new InvalidPasswordException("New password cannot be the same as the current password.");
        }

        // Encrypt and save new password
        existingUser.setPassword(
                passwordUtil.passwordEncrypt(dto.getNewPassword()));

        userRepository.save(existingUser);

        log.info("Password updated successfully for user {}",id);
    }
}
