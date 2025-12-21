package com.backend.MyBackend.controller;

import com.backend.MyBackend.dto.ApiResponse;
import com.backend.MyBackend.dto.UserDto;
import com.backend.MyBackend.service.AdminServiceInterface;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController{

    @Autowired
    private AdminServiceInterface adminService;

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> update(
            @PathVariable Long id,
            @RequestBody Map<String, Object> fields){

        try{
            UserDto updatedUser = adminService.updateFields(id,fields);

            return ResponseEntity.ok(new ApiResponse("User updated successfully",updatedUser));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

}
