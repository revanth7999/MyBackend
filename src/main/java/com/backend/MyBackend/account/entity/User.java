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

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public Timestamp getCreated_time_stamp(){
        return created_time_stamp;
    }

    public void setCreated_time_stamp(Timestamp created_time_stamp){
        this.created_time_stamp = created_time_stamp;
    }

    public Boolean getIsActive(){
        return isActive;
    }

    public void setIsActive(Boolean active){
        isActive = active;
    }

    public void setPassword(String password){
        password = password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public User(Long id,String password,String username,String role,Timestamp createdTimeStamp,Boolean isActive,
            String email,String address){
        this.id = id;
        this.password = password;
        this.username = username;
        this.role = role;
        this.created_time_stamp = createdTimeStamp;
        this.isActive = isActive;
        this.email = email;
        this.address = address;
    }

    public User(){
    }

    public String getId(){
        return String.valueOf(id);
    }
}
