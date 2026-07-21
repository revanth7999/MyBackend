package com.backend.MyBackend.notification.listener;

import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.account.event.UserRegisteredEvent;
import com.backend.MyBackend.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Handles {@link UserRegisteredEvent} by creating the default notifications for a newly registered user.
 *
 * <p>
 * Notifications are created synchronously as part of the registration workflow to ensure they are available immediately
 * after registration.
 * </p>
 *
 * <p>
 * Following notifications created:
 * <ul>
 * <li>Welcome notification.</li>
 * <li>Email verification reminder notification.</li>
 * </ul>
 */
@Component
public class NotificationEventListener{

    private final NotificationService notificationService;
    private static final Logger log = LoggerFactory.getLogger(NotificationEventListener.class);

    public NotificationEventListener(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @EventListener
    public void onUserRegistered(UserRegisteredEvent event){
        log.info("User registered event triggered");

        User user = event.getUser();
        notificationService.createWelcomeNotification(user);
        notificationService.createEmailVerificationNotification(user);
    }
}
