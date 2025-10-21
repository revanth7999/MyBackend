package com.backend.MyBackend.modal;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "login_session")
public class LoginSession{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Timestamp login_time = new Timestamp(System.currentTimeMillis());
    private Timestamp logout_time;
    private String ip_address;
    private String device_info;

    public LoginSession(){
    }

    public LoginSession(User user,String ipAddress,String deviceInfo){
        this.user = user;
        this.ip_address = ipAddress;
        this.device_info = deviceInfo;
    }

    // Getters and Setters
    public Long getId(){
        return id;
    }
    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }
    public Timestamp getLogin_time(){
        return login_time;
    }
    public void setLogin_time(Timestamp login_time){
        this.login_time = login_time;
    }
    public Timestamp getLogout_time(){
        return logout_time;
    }
    public void setLogout_time(Timestamp logout_time){
        this.logout_time = logout_time;
    }
    public String getIp_address(){
        return ip_address;
    }
    public void setIp_address(String ip_address){
        this.ip_address = ip_address;
    }
    public String getDevice_info(){
        return device_info;
    }
    public void setDevice_info(String device_info){
        this.device_info = device_info;
    }
}
