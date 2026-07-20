package com.backend.MyBackend.notification.repository;

import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.notification.entity.Notification;
import com.backend.MyBackend.notification.enums.NotificationCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long>{

    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    long countByUserIdAndIsReadFalse(Long userId);

    boolean existsByUserAndCode(User user,NotificationCode notificationCode);
}
