package com.backend.MyBackend.account.dto;

import static com.backend.MyBackend.common.util.PasswordUtil.isBlank;

public class LoginResponseDto{
    private Long userId;
    private String username;
    private String role;
    private String email;
    private boolean isEmailVerified;
    private String address;
    private String accessToken;
    private String refreshToken;

    // Getters

    public Long getUserId(){
        return userId;
    }
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
    public String getEmail(){
        return email;
    }
    public String getAddress(){
        return address;
    }
    public boolean getIsEmailVerified(){
        return isEmailVerified;
    }

    // Setters
    public void setUserId(Long userId){
        this.userId = userId;
    }
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
    public void setEmail(String email){
        this.email = email;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setIsEmailVerified(boolean isEmailVerified){
        this.isEmailVerified = isEmailVerified;
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
        this.userId = builder.userId;
        this.username = builder.username;
        this.role = builder.role;
        this.accessToken = builder.accessToken;
        this.refreshToken = builder.refreshToken;
        this.email = builder.email;
        this.address = builder.address;
        this.isEmailVerified = builder.isEmailVerified;
    }

    public static class LoginResponseDtoBuilder{
        private final Long userId;
        private final String username;
        private final String role;
        private String accessToken;
        private String refreshToken;
        private String email;
        private String address;
        private boolean isEmailVerified;

        public LoginResponseDtoBuilder(Long userId,String username,String role,boolean isEmailVerified){
            this.userId = userId;
            this.username = username;
            this.role = role;
            this.isEmailVerified = isEmailVerified;
        }

        public LoginResponseDtoBuilder accessToken(String accessToken){
            this.accessToken = accessToken;
            return this;
        }

        public LoginResponseDtoBuilder refreshToken(String refreshToken){
            this.refreshToken = refreshToken;
            return this;
        }

        public LoginResponseDtoBuilder email(String email){
            this.email = email;
            return this;
        }

        public LoginResponseDtoBuilder address(String address){
            this.address = address;
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
