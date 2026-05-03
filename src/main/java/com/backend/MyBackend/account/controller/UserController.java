package com.backend.MyBackend.account.controller;

import com.backend.MyBackend.account.service.UserService;
import com.backend.MyBackend.common.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev/users")
public class UserController{

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getAllUsers(){
        return ResponseEntity.ok(new ApiResponse("Rendered Successfully",userService.getAllUsers()));
    }
}
