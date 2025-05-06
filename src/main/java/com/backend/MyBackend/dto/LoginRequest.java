package com.backend.MyBackend.dto;

public class LoginRequest {
    private String username;
    private String role;
    private String token;

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

    public LoginRequest(String username, String role, String token) {
        this.username = username;
        this.role = role;
        this.token = token;
    }

    public LoginRequest() {}
}
