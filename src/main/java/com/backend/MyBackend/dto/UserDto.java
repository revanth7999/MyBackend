package com.backend.MyBackend.dto;

public class UserDto{

    private String id;
    private String username;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    private String role;
    private Boolean isActive;
    private String token;

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public UserDto(){
    }

    public Boolean getIsActive(){
        return isActive;
    }

    public void setisActive(Boolean isActive){
        this.isActive = isActive;
    }

    public UserDto(String id,String username,String occupation,Boolean isActive,String token){
        this.id = id;
        this.username = username;
        this.role = occupation;
        this.isActive = isActive;
        this.token = token;
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

    public void seRole(String occupation){
        this.role = occupation;
    }
}
