package com.backend.MyBackend.feignClient.service;

import com.backend.MyBackend.feignClient.dto.NotificationServiceApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "notification-service", url = "http://notification-service:8444")
public interface NotificationFeignClient{

    @GetMapping("/api/notifications/testing")
    NotificationServiceApiResponse getTest();

}
