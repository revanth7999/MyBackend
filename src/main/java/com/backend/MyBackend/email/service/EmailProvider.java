package com.backend.MyBackend.email.service;

public interface EmailProvider{
    void sendEmail(String to,String subject,String html);
}
