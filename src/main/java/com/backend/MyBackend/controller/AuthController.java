package com.backend.MyBackend.controller;

import com.backend.MyBackend.dto.ApiResponse;
import com.backend.MyBackend.dto.UserDto;
import com.backend.MyBackend.modal.User;
import com.backend.MyBackend.service.HeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dev")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private HeadService headService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user) {
        try {
            UserDto userDTO = headService.register(user);
            return ResponseEntity.ok(new ApiResponse(user.getUsername() + " is created successfully",userDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/allUsers")
    public ResponseEntity<ApiResponse> getAllUsers() {
        return ResponseEntity.ok(new ApiResponse("Rendered Successfully",headService.getAllUsers()));
    }
}
