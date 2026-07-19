package com.backend.MyBackend.notification.service;

import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.notification.dto.NotificationResponseDto;
import com.backend.MyBackend.notification.enums.NotificationActionType;
import com.backend.MyBackend.notification.enums.NotificationCategory;
import com.backend.MyBackend.notification.enums.NotificationPriority;
import com.backend.MyBackend.notification.enums.NotificationType;
import java.util.List;

public interface NotificationService{

    void createNotification(
            User user,
            String title,
            String message,
            NotificationType type,
            NotificationPriority priority,
            NotificationCategory category,
            NotificationActionType actionType);

    void createWelcomeNotification(User user);

    void createEmailVerificationNotification(User user);

    List<NotificationResponseDto> getNotifications(Long userId);
}
