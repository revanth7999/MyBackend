package com.backend.MyBackend.test;

import com.backend.MyBackend.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController{

    @GetMapping("/api/test")
    public ResponseEntity<ApiResponse> test(){
        return ResponseEntity.ok(
                new ApiResponse(
                        "Success",
                        "Application is running successfully over HTTPS!"));
    }
}
