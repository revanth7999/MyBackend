package com.backend.MyBackend.dto;

public class UserDto {

    private String username;
    private String role;
    private Boolean is_active;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto() {}

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public UserDto(String username, String occupation, Boolean is_active, String token) {
        this.username = username;
        this.role = occupation;
        this.is_active = is_active;
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

    public void seRole(String occupation) {
        this.role = occupation;
    }
}
