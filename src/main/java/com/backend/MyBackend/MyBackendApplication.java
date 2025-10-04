package com.backend.MyBackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyBackendApplication {

    public static void main(String[] args) {
        System.out.println("Starting MyBackendApplication...");
        System.out.println("JDBC URL: " + System.getenv("SPRING_DATASOURCE_URL"));
        System.out.println(args);
        SpringApplication.run(MyBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(JdbcConnectionDetails details) {
        return args -> {
            var info = String.format("""
                    JDBC URL: %s
                    Username: %s
                    Driver Class Name: %s
                    """,
                    details.getJdbcUrl(),
                    details.getUsername(),
                    details.getDriverClassName());
            System.out.println(info);
        };
    }
}