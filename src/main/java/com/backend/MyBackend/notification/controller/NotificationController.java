package com.backend.MyBackend.notification.controller;

import com.backend.MyBackend.common.dto.ApiResponse;
import com.backend.MyBackend.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController{

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getNotifications(@Valid @PathVariable Long userId){

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse("Notifications Foud",notificationService.getNotifications(userId)));
    }
}
