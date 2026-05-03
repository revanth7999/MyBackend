package com.backend.MyBackend.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil{

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String passwordEncrypt(String pass){
        return passwordEncoder.encode(pass);
    }

    public boolean passwordMatches(String rawPassword,String hashedPassword){
        return passwordEncoder.matches(rawPassword,hashedPassword);
    }

    public static boolean isBlank(String str){
        return str == null || str.trim().isEmpty();
    }
}
