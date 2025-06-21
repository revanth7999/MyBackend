package com.backend.MyBackend.modal;

import jakarta.persistence.*;

@Entity
@Table(name = "ROLES")
public class Roles{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roles;

    public String getRoles(){
        return roles;
    }

    public void setRoles(String roles){
        this.roles = roles;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Roles(Long id,String roles){
        this.id = id;
        this.roles = roles;
    }

    public Roles(){
    }
}
