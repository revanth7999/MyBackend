package com.backend.MyBackend.dto;

public class UserDto {

    private String username;
    private String role;

    public UserDto() {}

    public UserDto(String username, String occupation) {
        this.username = username;
        this.role = occupation;
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
