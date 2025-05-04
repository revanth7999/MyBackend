package com.backend.MyBackend.controller;

import com.backend.MyBackend.dto.ApiResponse;
import com.backend.MyBackend.service.HeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin(origins = "*")
public class RestaurantController {

    @Autowired
    private HeadService headService;

    @GetMapping("/allRestaurants")
    public ResponseEntity<ApiResponse> getallRestaurants() {
        return ResponseEntity.ok(new ApiResponse("Rendered Successfully", headService.getallRestaurants()));
    }
}
