package com.backend.MyBackend.account.controller;

import com.backend.MyBackend.account.dto.LoginRequestDto;
import com.backend.MyBackend.account.dto.LoginResponseDto;
import com.backend.MyBackend.account.dto.UserDto;
import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.account.service.UserService;
import com.backend.MyBackend.common.constants.Constants;
import com.backend.MyBackend.common.dto.ApiResponse;
import com.backend.MyBackend.common.util.CookieUtil;
import com.backend.MyBackend.common.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dev/auth")
@CrossOrigin(origins = "*")
public class AuthController{

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user){
        try{
            UserDto userDTO = userService.register(user);
            String token = JwtUtil.generateToken(user.getUsername(),userDTO.getRole());
            userDTO.setToken(token); // Add token to the response DTO
            return ResponseEntity.ok(new ApiResponse(user.getUsername() + Constants.USER_REGISTER_SUCCESS,userDTO));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequestDto loginRequestDto,HttpServletResponse response){
        try{
            // Create a User object from the DTO
            String username = loginRequestDto.getUsername();
            String password = loginRequestDto.getPassword();
            String deviceInfo = loginRequestDto.getDeviceInfo();

            LoginResponseDto loginResponseDto = userService.login(username,password,deviceInfo);
            String refreshToken = loginResponseDto.getRefreshToken();

            // Create HttpOnly cookie for refresh token
            Cookie refreshTokenCookie = new Cookie("refresh_token",refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(false); // set true if using HTTPS
            refreshTokenCookie.setPath("/"); // cookie valid for entire domain
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // e.g. 7 days expiry

            response.addCookie(refreshTokenCookie);

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
    public ResponseEntity<ApiResponse> logout(Authentication authentication){

        if (authentication == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("User not authenticated",null));
        }

        String username = authentication.getName();
        userService.logout(username);

        ResponseCookie refreshCookie = CookieUtil.deleteCookie("refresh_token");
        ResponseCookie sessionCookie = CookieUtil.deleteCookie("JSESSIONID");

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,refreshCookie.toString())
                .header(HttpHeaders.SET_COOKIE,sessionCookie.toString())
                .body(new ApiResponse("Logged out successfully",null));
    }

}
