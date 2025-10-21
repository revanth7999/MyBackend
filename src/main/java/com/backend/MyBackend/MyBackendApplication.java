package com.backend.MyBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyBackendApplication{

    public static void main(String[] args){
        System.out.println("🚀 Starting MyBackendApplication...");
        SpringApplication.run(MyBackendApplication.class,args);
    }
}
