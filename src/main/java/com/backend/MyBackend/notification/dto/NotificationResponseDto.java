package com.backend.MyBackend.notification.dto;

import com.backend.MyBackend.notification.enums.NotificationActionType;
import com.backend.MyBackend.notification.enums.NotificationCategory;
import com.backend.MyBackend.notification.enums.NotificationPriority;
import com.backend.MyBackend.notification.enums.NotificationType;
import java.time.LocalDateTime;

public record NotificationResponseDto(
        Long id,
        String title,
        String message,
        NotificationType type,
        NotificationPriority priority,
        NotificationCategory category,
        NotificationActionType actionType,
        Boolean isRead,
        LocalDateTime createdAt) {
}
