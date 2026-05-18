package com.backend.MyBackend.account.dto;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDto{

    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    private String deviceInfo;

    // Getters
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getDeviceInfo(){
        return deviceInfo;
    }

    // Setters
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setDeviceInfo(String deviceInfo){
        this.deviceInfo = deviceInfo;
    }
}
