package com.backend.MyBackend.starter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that prints database connection details (JDBC URL, username, driver) at application startup.
 *
 * <p>
 * This helps verify that Spring Boot has successfully loaded the datasource configuration and connected to the right
 * database.
 * </p>
 */
@Configuration
@ConditionalOnProperty(name = "app.logging_features.print_db_info", havingValue = "true")
public class DatabaseInfoPrinter{

    /**
     * CommandLineRunner bean that executes after the application context is fully initialized. Prints out database
     * details using {@link JdbcConnectionDetails}.
     *
     * @param details
     *            injected Spring Boot JDBC connection details
     * @return a CommandLineRunner that logs DB info to the console
     */
    @Bean
    CommandLineRunner printDatabaseDetails(JdbcConnectionDetails details){
        return args -> {
            var info = String.format("""
                    ========================================
                    🚀 DATABASE CONNECTION DETAILS
                    ========================================
                    JDBC URL: %s
                    Username: %s
                    Driver Class Name: %s
                    ========================================
                    """,
                    details.getJdbcUrl(),
                    details.getUsername(),
                    details.getDriverClassName());
            System.out.println(info);
        };
    }
}
