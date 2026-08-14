package com.backend.MyBackend.job.service;

import com.backend.MyBackend.account.dto.UserDto;
import com.backend.MyBackend.account.event.InactiveUsersEvent;
import com.backend.MyBackend.account.repository.LoginSessionRepository;
import com.backend.MyBackend.common.configurations.UserInactivityProperties;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobService{

    private static final Logger log = LoggerFactory.getLogger(JobService.class);
    private final ApplicationEventPublisher eventPublisher;
    private final LoginSessionRepository loginSessionRepository;
    private final UserInactivityProperties properties;

    public JobService(ApplicationEventPublisher eventPublisher,LoginSessionRepository loginSessionRepository,
            UserInactivityProperties properties){
        this.eventPublisher = eventPublisher;
        this.loginSessionRepository = loginSessionRepository;
        this.properties = properties;
    }

    /**
     * Fetches users who have been inactive for more than 90 days and publishes an event to process them.
     *
     * @return an empty list of UserDto
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public List<UserDto> fetchInactiveUsers(){
        log.info("==== [INACTIVE USER JOB START] ====");

        int days = properties.getDays();
        log.info("Checking for users inactive for more than {} days.",days);
        LocalDateTime ninetyDaysAgo = LocalDateTime.now().minusDays(days);
        Timestamp ninetyDaysAgoTs = Timestamp.valueOf(ninetyDaysAgo);

        // Get all users who have not logged in within the last 90 days
        List<Long> idsToDeactivate = loginSessionRepository.findUserIdsSince(ninetyDaysAgoTs);
        log.info(
                "Found {} inactive users eligible for deactivation.",
                idsToDeactivate.size());
        if (idsToDeactivate.isEmpty()){
            log.info("No inactive users found. Skipping event publication.");
            log.info("==== [INACTIVE USER JOB END] ====");
            return List.of();
        }

        log.info("Publishing InactiveUsersEvent for {} users.",idsToDeactivate.size());
        eventPublisher.publishEvent(new InactiveUsersEvent(idsToDeactivate));
        log.info("InactiveUsersEvent published successfully.");
        log.info("==== [INACTIVE USER JOB END] ====");
        return List.of();
    }
}
