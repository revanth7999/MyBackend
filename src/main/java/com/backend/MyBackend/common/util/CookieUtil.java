package com.backend.MyBackend.common.util;

import org.springframework.http.ResponseCookie;

public class CookieUtil{

    public static ResponseCookie deleteCookie(String name){
        return ResponseCookie.from(name,"")
                .httpOnly(true)
                .secure(false) // true in prod
                .path("/")
                .maxAge(0)
                .build();
    }
}
