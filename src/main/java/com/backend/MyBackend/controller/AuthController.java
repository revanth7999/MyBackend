package com.backend.MyBackend.controller;

import com.backend.MyBackend.Constants;
import com.backend.MyBackend.config.JwtUtil;
import com.backend.MyBackend.dto.ApiResponse;
import com.backend.MyBackend.dto.LoginRequest;
import com.backend.MyBackend.dto.UserDto;
import com.backend.MyBackend.modal.User;
import com.backend.MyBackend.service.HeadService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/dev")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private HeadService headService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
        try {
            UserDto userDTO = headService.register(user);
            String token = JwtUtil.generateToken(user.getUsername(), userDTO.getRole());
            userDTO.setToken(token); // Add token to the response DTO
            return ResponseEntity.ok(new ApiResponse(user.getUsername() + Constants.USER_REGISTER_SUCCESS, userDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody User user, HttpServletResponse response) {
        try {
            LoginRequest loginRequest = headService.login(user.getUsername(), user.getPassword());
            String token = JwtUtil.generateToken(user.getUsername(), loginRequest.getRole());
            String refreshToken = JwtUtil.generateRefreshToken(user.getUsername()); // New method
            loginRequest.setToken(token); // Add token to the response DTO
            loginRequest.setRefreshToken(refreshToken);

            // Create HttpOnly cookie for refresh token
            Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(false); // set true if using HTTPS
            refreshTokenCookie.setPath("/"); // cookie valid for entire domain
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // e.g. 7 days expiry

            response.addCookie(refreshTokenCookie);

            return ResponseEntity.ok(new ApiResponse(Constants.LOGIN_SUCCESS, loginRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getAllUsers() {
        return ResponseEntity.ok(new ApiResponse("Rendered Successfully", headService.getAllUsers()));
    }

    @GetMapping("/admin/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getAllRoles() {
        return ResponseEntity.ok(new ApiResponse("Rendered Successfully", headService.getAllRoles()));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue("refresh_token") String refreshToken) {
        if (JwtUtil.validateToken(refreshToken)) {
            String username = JwtUtil.getUsernameFromToken(refreshToken);
            String role = headService.getRoleForUser(username);
            String newAccessToken = JwtUtil.generateToken(username, role);
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie deleteCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/dev/auth/refresh")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body("Logged out successfully");
    }


}
