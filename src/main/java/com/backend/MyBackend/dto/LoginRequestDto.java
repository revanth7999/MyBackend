package com.backend.MyBackend.dto;

public class LoginRequestDto{
    private String username;
    private String password;
    private String deviceInfo;

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getDeviceInfo(){
        return deviceInfo;
    }
    public void setDeviceInfo(String deviceInfo){
        this.deviceInfo = deviceInfo;
    }
}
