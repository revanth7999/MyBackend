package com.backend.MyBackend.modal;

import jakarta.persistence.*;

@Entity
@Table(name= "CAPTAIN")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String occupation;
    private String Password;


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


    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public User(Long id, String password, String username, String occupation) {
        this.id = id;
        this.Password = password;
        this.username = username;
        this.occupation = occupation;
    }

    public User() {
    }
}
