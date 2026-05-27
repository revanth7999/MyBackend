package com.backend.MyBackend.account.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateUserDto{

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
    private String role;
    private Boolean is_active;

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getRole(){
        return role;
    }
    public Boolean getIs_active(){
        return is_active;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setRole(String role){
        this.role = role;
    }
    public void setIs_active(Boolean is_active){
        this.is_active = is_active;
    }
}
