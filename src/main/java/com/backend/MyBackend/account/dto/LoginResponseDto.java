package com.backend.MyBackend.account.dto;

import static com.backend.MyBackend.common.util.PasswordUtil.isBlank;

public class LoginResponseDto{
    private String username;
    private String role;
    private String accessToken;
    private String refreshToken;

    // Getters
    public String getUsername(){
        return username;
    }
    public String getRole(){
        return role;
    }
    public String getAccessToken(){
        return accessToken;
    }
    public String getRefreshToken(){
        return refreshToken;
    }

    // Setters
    public void setUsername(String username){
        this.username = username;
    }
    public void setRole(String role){
        this.role = role;
    }
    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }
    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    // Generic Constructor
    // public LoginResponseDto(String username,String role,String accessToken,String refreshToken){
    // this.username = username;
    // this.role = role;
    // this.accessToken = accessToken;
    // this.refreshToken = refreshToken;
    // }

    public LoginResponseDto(){
    }

    public LoginResponseDto(LoginResponseDtoBuilder builder){
        this.username = builder.username;
        this.role = builder.role;
        this.accessToken = builder.accessToken;
        this.refreshToken = builder.refreshToken;
    }

    public static class LoginResponseDtoBuilder{
        private final String username;
        private final String role;
        private String accessToken;
        private String refreshToken;

        public LoginResponseDtoBuilder(String username,String role){
            this.username = username;
            this.role = role;
        }

        public LoginResponseDtoBuilder accessToken(String accessToken){
            this.accessToken = accessToken;
            return this;
        }

        public LoginResponseDtoBuilder refreshToken(String refreshToken){
            this.refreshToken = refreshToken;
            return this;
        }

        public LoginResponseDto build(){
            if (isBlank(username) || isBlank(role)){
                throw new IllegalStateException("Required username and role");
            }
            return new LoginResponseDto(this);
        }

    }
}
