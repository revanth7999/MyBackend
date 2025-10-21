package com.backend.MyBackend.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @Column(name = "login_time")
    private Timestamp loginTime = new Timestamp(System.currentTimeMillis());
    private Timestamp logoutTime;
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
    public Timestamp getLoginTime(){
        return loginTime;
    }
    public void setLoginTime(Timestamp loginTime){
        this.loginTime = loginTime;
    }
    public Timestamp getLogoutTime(){
        return logoutTime;
    }
    public void setLogoutTime(Timestamp logoutTime){
        this.logoutTime = logoutTime;
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
