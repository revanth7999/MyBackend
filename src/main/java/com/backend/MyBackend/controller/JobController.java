package com.backend.MyBackend.controller;

import com.backend.MyBackend.dto.ApiResponse;
import com.backend.MyBackend.dto.UserDto;
import com.backend.MyBackend.jobs.JobService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController{

    @Autowired
    private JobService jobService;

    @GetMapping("/inactive-users")
    public ResponseEntity<ApiResponse> getAllInactiveUsers(){
        List<UserDto> inactiveUsers = jobService.fetchInactiveUsers();
        return ResponseEntity.ok(new ApiResponse("Inactive users fetched successfully",inactiveUsers));
    }
}
