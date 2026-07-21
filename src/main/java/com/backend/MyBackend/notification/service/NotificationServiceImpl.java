package com.backend.MyBackend.notification.service;

import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.notification.constants.Constants;
import com.backend.MyBackend.notification.dto.NotificationResponseDto;
import com.backend.MyBackend.notification.entity.Notification;
import com.backend.MyBackend.notification.enums.NotificationActionType;
import com.backend.MyBackend.notification.enums.NotificationCategory;
import com.backend.MyBackend.notification.enums.NotificationCode;
import com.backend.MyBackend.notification.enums.NotificationPriority;
import com.backend.MyBackend.notification.enums.NotificationType;
import com.backend.MyBackend.notification.repository.NotificationRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

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
            NotificationActionType actionType,
            NotificationCode code){

        log.info("Creating notification for userId={}, title='{}', type={}, category={}",
                user.getId(),title,type,category);

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
                .code(code)
                .build();

        notificationRepository.save(notification);
        log.info("Notification created successfully. notificationId={}, userId={}",
                notification.getId(),user.getId());
    }

    @Override
    public void createWelcomeNotification(User user){
        log.info("Creating welcome notification for userId={}",user.getId());
        createNotification(
                user,
                Constants.WELCOME_TITLE,
                Constants.WELCOME_MESSAGE,
                NotificationType.SUCCESS,
                NotificationPriority.LOW,
                NotificationCategory.ACCOUNT,
                NotificationActionType.NONE,
                NotificationCode.WELCOME);
    }

    @Override
    public void createEmailVerificationNotification(User user){
        log.info("Creating email verification notification for userId={}",user.getId());
        createNotification(
                user,
                Constants.VERIFY_EMAIL_TITLE,
                Constants.VERIFY_EMAIL_MESSAGE,
                NotificationType.WARNING,
                NotificationPriority.HIGH,
                NotificationCategory.ACCOUNT,
                NotificationActionType.VERIFY_EMAIL,
                NotificationCode.EMAIL_VERIFICATION);
    }

    @Override
    public List<NotificationResponseDto> getNotifications(Long userId){
        log.debug("Fetching notifications for userId={}",userId);
        List<NotificationResponseDto> notifications = notificationRepository
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
                        notification.getCreatedAt(),notification.getCode()))
                .toList();

        log.debug("Found {} notifications for userId={}",
                notifications.size(),userId);

        return notifications;
    }

    @Override
    public void ensureDefaultNotifications(User user){
        log.debug("Ensuring default notifications for userId={}",user.getId());
        if (!notificationRepository.existsByUserAndCode(user,NotificationCode.WELCOME)){
            log.info("Welcome notification missing for userId={}. Creating it.",
                    user.getId());
            createWelcomeNotification(user);
        }

        if (!user.getIsEmailVerified()
                && !notificationRepository.existsByUserAndCode(
                        user,NotificationCode.EMAIL_VERIFICATION)){

            log.info("Email verification notification missing for userId={}. Creating it.",
                    user.getId());
            createEmailVerificationNotification(user);
        }

        log.debug("Default notification check completed for userId={}",user.getId());
    }

    @Override
    public void markEmailVerificationNotificationAsRead(User user){

        Notification notification = notificationRepository.findByUserAndCode(
                user,
                NotificationCode.EMAIL_VERIFICATION);

        if (notification == null){
            return;
        }

        notification.setIsRead(true);
        notification.setReadAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }
}
