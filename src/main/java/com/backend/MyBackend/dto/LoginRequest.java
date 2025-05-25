package com.backend.MyBackend.dto;

public class LoginRequest {
    private String username;
    private String role;
    private String token;
    private String refreshToken;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LoginRequest(String username, String role, String token, String refreshToken) {
        this.username = username;
        this.role = role;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public LoginRequest() {}
}
