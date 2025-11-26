package com.backend.MyBackend.dto;

public class LoginResponseDto{
    private String username;
    private String role;
    private String accessToken;
    private String refreshToken;

    public String getAccessToken(){
        return accessToken;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getRefreshToken(){
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public LoginResponseDto(String username,String role,String accessToken,String refreshToken){
        this.username = username;
        this.role = role;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public LoginResponseDto(){
    }
}
