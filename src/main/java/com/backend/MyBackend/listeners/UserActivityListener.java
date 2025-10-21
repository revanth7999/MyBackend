package com.backend.MyBackend.listeners;

import com.backend.MyBackend.events.InactiveUsersEvent;
import com.backend.MyBackend.repository.UserRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserActivityListener listens for InactiveUsersEvent and deactivates the specified users.
 */
@Component
public class UserActivityListener{

    private static final Logger log = LoggerFactory.getLogger(UserActivityListener.class);

    private final UserRepository userRepository;

    public UserActivityListener(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Handles the InactiveUsersEvent by deactivating the users with the given IDs.
     *
     * @param event the InactiveUsersEvent containing user IDs to deactivate
     */
    @Transactional
    @EventListener
    public void handleInactiveUsersEvent(InactiveUsersEvent event){
        List<Long> idsToDeactivate = event.getUserIds();

        if (idsToDeactivate == null || idsToDeactivate.isEmpty()){
            log.info("No users to deactivate.");
            return;
        }

        log.info("Deactivating users (ids): {}",idsToDeactivate);
        int updated = userRepository.deactivateUsersByIds(idsToDeactivate);
        log.info("Deactivated {} users.",updated);
    }
}
