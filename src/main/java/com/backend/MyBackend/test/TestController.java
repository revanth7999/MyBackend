package com.backend.MyBackend.test;

import com.backend.MyBackend.common.dto.ApiResponse;
import com.backend.MyBackend.feignClient.dto.NotificationServiceApiResponse;
import com.backend.MyBackend.feignClient.service.NotificationFeignClient;
import com.backend.MyBackend.kafka.KafkaProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController{
    private final KafkaProducer kafkaProducer;
    private final NotificationFeignClient notificationFeignClient;

    public TestController(KafkaProducer kafkaProducer,NotificationFeignClient notificationFeignClient){
        this.kafkaProducer = kafkaProducer;
        this.notificationFeignClient = notificationFeignClient;
    }

    @GetMapping("/api/test")
    public ResponseEntity<ApiResponse> test(){
        NotificationServiceApiResponse response = notificationFeignClient.getTest();

        return ResponseEntity.ok(
                new ApiResponse(
                        "Success, Application is running successfully over HTTPS!",
                        response));
    }

    @GetMapping("/api/kafka/test")
    public ResponseEntity<ApiResponse> testKafka(){
        kafkaProducer.sendMessage("Hello Kafka from User Service");

        return ResponseEntity.ok(
                new ApiResponse(
                        "Success",
                        "Message sent to Kafka successfully!"));
    }
}
