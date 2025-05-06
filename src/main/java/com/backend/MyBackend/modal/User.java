package com.backend.MyBackend.modal;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "CAPTAIN")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String role;
    private String Password;
    private Boolean is_active;
    private Timestamp created_time_stamp;

    public Timestamp getCreated_time_stamp() {
        return created_time_stamp;
    }

    public void setCreated_time_stamp(Timestamp created_time_stamp) {
        this.created_time_stamp = created_time_stamp;
    }

    public Boolean getis_active() {
        return is_active;
    }

    public void setis_active(Boolean active) {
        is_active = active;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return Password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(Long id, String password, String username, String role, Timestamp createdTimeStamp, Boolean is_active) {
        this.id = id;
        this.Password = password;
        this.username = username;
        this.role = role;
        this.created_time_stamp = createdTimeStamp;
        this.is_active = is_active;
    }

    public User() {}
}
