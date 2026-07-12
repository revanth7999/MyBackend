package com.backend.MyBackend.common.dto;

public class UserDetailsDto{
    private final Long id;
    private final String username;
    private final String role;
    private final String email;
    private final boolean emailVerified;
    private final String address;

    private UserDetailsDto(UserDtoBuilder builder){
        this.id = builder.id;
        this.username = builder.username;
        this.role = builder.role;
        this.email = builder.email;
        this.emailVerified = builder.emailVerified;
        this.address = builder.address;
    }

    public Long getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getRole(){
        return role;
    }
    public String getEmail(){
        return email;
    }
    public boolean getEmailVerified(){
        return emailVerified;
    }
    public String getAddress(){
        return address;
    }

    public static class UserDtoBuilder{
        private final Long id;
        private final String username;
        private final String role;
        private String email;
        private boolean emailVerified;
        private String address;

        public UserDtoBuilder(Long id,String username,String role){
            this.id = id;
            this.username = username;
            this.role = role;
        }

        public UserDtoBuilder email(String email){
            this.email = email;
            return this;
        }

        public UserDtoBuilder emailVerified(boolean emailVerified){
            this.emailVerified = emailVerified;
            return this;
        }

        public UserDtoBuilder address(String address){
            this.address = address;
            return this;
        }

        public UserDetailsDto build(){
            return new UserDetailsDto(this);
        }
    }
}
