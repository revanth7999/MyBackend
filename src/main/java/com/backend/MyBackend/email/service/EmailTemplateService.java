package com.backend.MyBackend.email.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateService{

    public String welcomeEmail(String username){

        String html = loadTemplate("templates/email/welcome-email.html");

        return html.replace("{{username}}",username);
    }

    public String verificationEmail(String verificationUrl){

        String html = loadTemplate(
                "templates/email/verification-email.html");

        return html.replace(
                "{{verificationUrl}}",
                verificationUrl);
    }

    private String loadTemplate(String path){
        try{
            ClassPathResource resource = new ClassPathResource(path);
            return new String(
                    resource.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8);
        } catch (IOException e){
            throw new RuntimeException("Unable to load email template.",e);
        }
    }

}
