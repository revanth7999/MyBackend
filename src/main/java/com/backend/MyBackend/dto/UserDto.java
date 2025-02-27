package com.backend.MyBackend.dto;

public class UserDto {

    private String username;
    private String occupation;

    public UserDto() {
    }

    public UserDto(String username, String occupation) {
        this.username = username;
        this.occupation = occupation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getoccupation() {
        return occupation;
    }

    public void setoccupation(String occupation) {
        this.occupation = occupation;
    }


}
