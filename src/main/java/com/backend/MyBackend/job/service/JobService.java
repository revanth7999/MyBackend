package com.backend.MyBackend.job.service;

import com.backend.MyBackend.account.dto.UserDto;
import com.backend.MyBackend.account.event.InactiveUsersEvent;
import com.backend.MyBackend.account.repository.LoginSessionRepository;
import com.backend.MyBackend.account.repository.UserRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobService{

    private static final Logger log = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private LoginSessionRepository loginSessionRepository;

    /**
     * Fetches users who have been inactive for more than 90 days and publishes an event to process them.
     *
     * @return an empty list of UserDto
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public List<UserDto> fetchInactiveUsers(){
        log.info("🚀 [JOB START] ⏰ Checking recent login sessions..");

        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(90);
        Timestamp oneDayAgoTs = Timestamp.valueOf(oneDayAgo);

        // Get all users who logged in within last 90 days
        List<Long> idsToDeactivate = loginSessionRepository.findUserIdsSince(oneDayAgoTs);

        // Publish event to process inactive users
        log.info(("Publishing InactiveUsersEvent for users to deactivate."));
        eventPublisher.publishEvent(new InactiveUsersEvent(idsToDeactivate));
        log.info("Published InactiveUsersEvent.");

        log.info("✅ [JOB END] ⏰ Inactive users processed.");
        return List.of();
    }
}
