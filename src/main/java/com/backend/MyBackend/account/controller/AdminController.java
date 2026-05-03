package com.backend.MyBackend.account.controller;

import com.backend.MyBackend.account.dto.UserDto;
import com.backend.MyBackend.account.service.AdminServiceInterface;
import com.backend.MyBackend.account.service.UserService;
import com.backend.MyBackend.common.dto.ApiResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev/admin")
@CrossOrigin(origins = "*")
public class AdminController{

    @Autowired
    private UserService userService;

    @Autowired
    private AdminServiceInterface adminService;

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getAllRoles(){
        return ResponseEntity.ok(new ApiResponse("Rendered Successfully",userService.getAllRoles()));
    }

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
