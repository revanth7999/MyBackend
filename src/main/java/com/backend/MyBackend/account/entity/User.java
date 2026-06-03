package com.backend.MyBackend.account.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "captain")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_time_stamp")
    private Timestamp created_time_stamp;
    private String email;
    private String address;

    @Column(name = "is_email_verified")
    private boolean isEmailVerified = false;
    @Column(name = "email_verification_token")
    private String emailVerificationToken;
    @Column(name = "email_verification_expiry")
    private Timestamp emailVerificationExpiry;

    // Getters
    public Long getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getRole(){
        return role;
    }
    public String getPassword(){
        return password;
    }
    public Boolean getIsActive(){
        return isActive;
    }
    public Timestamp getCreated_time_stamp(){
        return created_time_stamp;
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
    public String getEmailVerificationToken(){
        return emailVerificationToken;
    }
    public Timestamp getEmailVerificationExpiry(){
        return emailVerificationExpiry;
    }

    // Setters
    public void setUsername(String username){
        this.username = username;
    }
    public void setRole(String role){
        this.role = role;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setIsActive(Boolean active){
        isActive = active;
    }
    public void setCreated_time_stamp(Timestamp created_time_stamp){
        this.created_time_stamp = created_time_stamp;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setEmailVerified(boolean emailVerified){
        isEmailVerified = emailVerified;
    }
    public void setEmailVerificationToken(String emailVerificationToken){
        this.emailVerificationToken = emailVerificationToken;
    }
    public void setEmailVerificationExpiry(Timestamp emailVerificationExpiry){
        this.emailVerificationExpiry = emailVerificationExpiry;
    }

    public User(){
    }
    public User(Long id,String password,String username,String role,Timestamp createdTimeStamp,Boolean isActive,
            String email,String address,Boolean isEmailVerified,String emailVerificationToken,
            Timestamp emailVerificationExpiry){
        this.id = id;
        this.password = password;
        this.username = username;
        this.role = role;
        this.created_time_stamp = createdTimeStamp;
        this.isActive = isActive;
        this.email = email;
        this.address = address;
        this.isEmailVerified = isEmailVerified;
        this.emailVerificationToken = emailVerificationToken;
        this.emailVerificationExpiry = emailVerificationExpiry;
    }
}
