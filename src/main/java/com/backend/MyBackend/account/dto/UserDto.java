package com.backend.MyBackend.account.dto;

public class UserDto{

    private String id;
    private String username;
    private String role;
    private Boolean isActive;
    private String token;
    private String email;
    private String address;

    // Getters
    public String getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getRole(){
        return role;
    }
    public Boolean getIsActive(){
        return isActive;
    }
    public String getToken(){
        return token;
    }
    public String getEmail(){
        return email;
    }
    public String getAddress(){
        return address;
    }

    // Setters
    public void setId(String id){
        this.id = id;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setRole(String occupation){
        this.role = occupation;
    }
    public void setIsActive(Boolean isActive){
        this.isActive = isActive;
    }
    public void setToken(String token){
        this.token = token;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setAddress(String address){
        this.address = address;
    }

    // No-Args Constructor
    public UserDto(){
    }

    /**
     * UserDto Constructor with Builder Design pattern.
     */
    public UserDto(UserDtoBuilder builder){
        this.id = builder.id;
        this.username = builder.username;
        this.role = builder.role;
        this.isActive = builder.isActive;
        this.token = builder.token;
        this.email = builder.email;
        this.address = builder.address;
    }

    /**
     * Builder Design pattern
     */
    public static class UserDtoBuilder{
        private final String id;
        private final String username;
        private final String role;
        private final Boolean isActive;
        private String token;
        private String email;
        private String address;

        public UserDtoBuilder(String id,String username,String role,Boolean isActive){
            this.id = id;
            this.username = username;
            this.role = role;
            this.isActive = isActive;
        }

        public UserDtoBuilder token(String token){
            this.token = token;
            return this;
        }
        public UserDtoBuilder email(String email){
            this.email = email;
            return this;
        }
        public UserDtoBuilder address(String address){
            this.address = address;
            return this;
        }
        public UserDto build(){
            return new UserDto(this);
        }
    }

    // Generic Constructor
    // public UserDto(String id,String username,String occupation,Boolean isActive,String token,String email,
    // String address){
    // this.id = id;
    // this.username = username;
    // this.role = occupation;
    // this.isActive = isActive;
    // this.token = token;
    // this.email = email;
    // this.address = address;
    // }
}
