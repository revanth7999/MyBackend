package com.backend.MyBackend.job.service;

import com.backend.MyBackend.common.configurations.DynamicConfigManager;
import java.sql.Connection;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class HealthCheckScheduler{

    private final DynamicConfigManager configManager;
    private final DataSource dataSource;

    @Scheduled(fixedRate = 840_000)
    public void checkDatabaseHealth(){

        try{

            String enabledValue = configManager.getProperty(
                    "background.health-check.enabled");

            boolean enabled = Boolean.parseBoolean(enabledValue);

            log.info(
                    "Database health check configuration: enabled={}",
                    enabled);

            if (!enabled){
                log.info("Database health check job is disabled.");
                return;
            }

            log.info("==== [DATABASE HEALTH CHECK START] ====");

            try (Connection connection = dataSource.getConnection()){

                if (connection.isValid(2)){
                    log.info("Database health check successful. Status: UP");
                } else{
                    log.warn("Database health check completed. Status: DOWN");
                }
            }

            log.info("==== [DATABASE HEALTH CHECK END] ====");

        } catch (Exception e){
            log.error("Failed to execute database health check.",e);
        }
    }
}
