package com.backend.MyBackend.account.dto;

public class LoginRequestDto{
    private String username;
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
