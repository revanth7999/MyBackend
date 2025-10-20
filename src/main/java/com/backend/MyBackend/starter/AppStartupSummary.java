package com.backend.MyBackend.starter;

import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Component that prints a summary of application startup details such as active profiles, server port, and application
 * name.
 *
 * <p>
 * This runs automatically after the Spring Boot application has started, provided the property
 * {@code app.logging_features.print_app_summary=true} is set.
 * </p>
 */
@Component
@ConditionalOnProperty(name = "app.logging_features.print_app_summary", havingValue = "true")
public class AppStartupSummary implements CommandLineRunner{

    private final Environment env;

    public AppStartupSummary(Environment env){
        this.env = env;
    }

    @Override
    public void run(String... args){
        System.out.printf("""
                ========================================
                🌱 APPLICATION STARTUP SUMMARY
                ========================================
                Active Profile(s): %s
                Server Port: %s
                Application Name: %s
                ========================================
                """,
                Arrays.toString(env.getActiveProfiles()),
                env.getProperty("server.port","8080"),
                env.getProperty("spring.application.name","MyBackend"));
    }
}
