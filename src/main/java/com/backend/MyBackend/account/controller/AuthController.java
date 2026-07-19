package com.backend.MyBackend.account.controller;

import com.backend.MyBackend.account.dto.ChangePasswordRequestDto;
import com.backend.MyBackend.account.dto.CreateUserDto;
import com.backend.MyBackend.account.dto.LoginRequestDto;
import com.backend.MyBackend.account.dto.LoginResponseDto;
import com.backend.MyBackend.account.dto.RegisterUserResponseDto;
import com.backend.MyBackend.account.service.UserService;
import com.backend.MyBackend.common.constants.Constants;
import com.backend.MyBackend.common.dto.ApiResponse;
import com.backend.MyBackend.common.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController{

    @Autowired
    private UserService userService;

    @Value("${app.environment}")
    private String environment;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody CreateUserDto createUserDto){
        RegisterUserResponseDto registerUserResponseDtoDTO = userService.register(createUserDto);
        return ResponseEntity
                .ok(new ApiResponse(createUserDto.getUsername() + Constants.USER_REGISTER_SUCCESS,
                        registerUserResponseDtoDTO));

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequestDto loginRequestDto,
            HttpServletResponse response){
        try{
            String username = loginRequestDto.getUsername();
            String password = loginRequestDto.getPassword();
            String deviceInfo = loginRequestDto.getDeviceInfo();

            LoginResponseDto loginResponseDto = userService.login(username,password,deviceInfo);
            String refreshToken = loginResponseDto.getTokens().getRefreshToken();

            boolean isProd = environment.equals("prod");

            ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token",refreshToken)
                    .httpOnly(true)
                    .secure(isProd) // false on local, true on prod
                    .sameSite(isProd ? "None" : "Lax") // None on prod, Lax on local
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString());

            return ResponseEntity.ok(new ApiResponse(Constants.LOGIN_SUCCESS,loginResponseDto));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue("refresh_token") String refreshToken){
        if (JwtUtil.validateToken(refreshToken)){
            String username = JwtUtil.getUsernameFromToken(refreshToken);
            String role = userService.getRoleForUser(username);
            String newAccessToken = JwtUtil.generateToken(username,role);
            return ResponseEntity.ok(Map.of("accessToken",newAccessToken));
        } else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletRequest request,
            HttpServletResponse response){

        boolean isProd = environment.equals("prod");

        // clears the HttpOnly cookie
        ResponseCookie clearCookie = ResponseCookie.from("refresh_token","")
                .httpOnly(true)
                .secure(isProd)
                .sameSite(isProd ? "None" : "Lax")
                .path("/")
                .maxAge(0) // expires immediately
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE,clearCookie.toString());

        return ResponseEntity.ok(new ApiResponse("Logged out successfully",null));
    }

    @GetMapping("/check-username")
    public ResponseEntity<ApiResponse> checkUsername(
            @RequestParam @NotBlank String username){

        boolean available = userService.isUsernameAvailable(username);
        String message = available
                ? Constants.USERNAME_UNAVAILABLE
                : Constants.USERNAME_AVAILABLE;

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse(message,available));
    }

    @PostMapping("/change-password/{id}")
    public ResponseEntity<ApiResponse> changePassword(
            @Valid @PathVariable Long id,
            @RequestBody ChangePasswordRequestDto changePasswordRequestDto){
        userService.updatePassword(id,changePasswordRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Password was updated Successfully",null));

    }

}
