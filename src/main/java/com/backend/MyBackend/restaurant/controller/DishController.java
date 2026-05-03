package com.backend.MyBackend.restaurant.controller;

import com.backend.MyBackend.restaurant.entity.Dishes;
import com.backend.MyBackend.restaurant.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dishes")
public class DishController{

    @Autowired
    private DishService dishService;

    @PostMapping("/restaurant/{restaurantId}")
    public Dishes addDish(
            @PathVariable Long restaurantId,
            @RequestBody Dishes dish){

        return dishService.addDish(restaurantId,dish);
    }
}
