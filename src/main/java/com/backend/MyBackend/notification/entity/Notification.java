package com.backend.MyBackend.notification.entity;

import com.backend.MyBackend.account.entity.User;
import com.backend.MyBackend.notification.enums.NotificationActionType;
import com.backend.MyBackend.notification.enums.NotificationCategory;
import com.backend.MyBackend.notification.enums.NotificationCode;
import com.backend.MyBackend.notification.enums.NotificationPriority;
import com.backend.MyBackend.notification.enums.NotificationType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Notification recipient
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false, length = 50)
    private NotificationActionType actionType;

    /**
     * JSON payload for the action. Example: {"orderId":10} {"restaurantId":25}
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "action_payload", columnDefinition = "jsonb")
    private String actionPayload;

    @Column(name = "is_read", nullable = false)
    @Builder.Default
    private Boolean isRead = false;

    @Column(name = "is_dismissed", nullable = false)
    @Builder.Default
    private Boolean isDismissed = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_code", nullable = false, length = 50)
    private NotificationCode code;
}
