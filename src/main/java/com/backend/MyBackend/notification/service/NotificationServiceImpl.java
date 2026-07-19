package com.backend.MyBackend.notification.service;

import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.notification.constants.Constants;
import com.backend.MyBackend.notification.dto.NotificationResponseDto;
import com.backend.MyBackend.notification.entity.Notification;
import com.backend.MyBackend.notification.enums.NotificationActionType;
import com.backend.MyBackend.notification.enums.NotificationCategory;
import com.backend.MyBackend.notification.enums.NotificationPriority;
import com.backend.MyBackend.notification.enums.NotificationType;
import com.backend.MyBackend.notification.repository.NotificationRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void createNotification(
            User user,
            String title,
            String message,
            NotificationType type,
            NotificationPriority priority,
            NotificationCategory category,
            NotificationActionType actionType){

        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .message(message)
                .type(type)
                .priority(priority)
                .category(category)
                .actionType(actionType)
                .isRead(false)
                .isDismissed(false)
                .build();

        notificationRepository.save(notification);
    }

    @Override
    public void createWelcomeNotification(User user){
        createNotification(
                user,
                Constants.WELCOME_TITLE,
                Constants.WELCOME_MESSAGE,
                NotificationType.SUCCESS,
                NotificationPriority.LOW,
                NotificationCategory.ACCOUNT,
                NotificationActionType.NONE);
    }

    @Override
    public void createEmailVerificationNotification(User user){
        createNotification(
                user,
                Constants.VERIFY_EMAIL_TITLE,
                Constants.VERIFY_EMAIL_MESSAGE,
                NotificationType.WARNING,
                NotificationPriority.HIGH,
                NotificationCategory.ACCOUNT,
                NotificationActionType.VERIFY_EMAIL);
    }

    @Override
    public List<NotificationResponseDto> getNotifications(Long userId){

        return notificationRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(notification -> new NotificationResponseDto(
                        notification.getId(),
                        notification.getTitle(),
                        notification.getMessage(),
                        notification.getType(),
                        notification.getPriority(),
                        notification.getCategory(),
                        notification.getActionType(),
                        notification.getIsRead(),
                        notification.getCreatedAt()))
                .toList();
    }
}
