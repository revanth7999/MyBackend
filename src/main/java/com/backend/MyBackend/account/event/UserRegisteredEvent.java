package com.backend.MyBackend.account.event;

import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.notification.listener.NotificationEventListener;

/**
 * Event published after a user has been successfully registered.
 *
 * <p>
 * This event triggers post-registration workflows such as creating notifications, sending emails, and preparing account
 * verification.
 *
 * <p>
 * Current event consumers:
 * <ul>
 * <li>{@code WelcomeEmailListener}</li>
 * <li>{@link NotificationEventListener}</li>
 * <li>{@code VerificationRegistrationListener}</li>
 * </ul>
 *
 * <p>
 * The event is published only after the user has been successfully persisted.
 * </p>
 */
public class UserRegisteredEvent{
    private final User user;

    public UserRegisteredEvent(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public String email(){
        return user.getEmail();
    }

    public String userName(){
        return user.getUsername();
    }

    public Long userId(){
        return user.getId();
    }
}
