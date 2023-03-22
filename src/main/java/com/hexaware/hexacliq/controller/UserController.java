package com.hexaware.hexacliq.controller;

import com.hexaware.hexacliq.dto.UserDto;
import com.hexaware.hexacliq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{userName}")
    public UserDto findByUserName(@PathVariable String userName) {
        return userService.getUserByName(userName).orElseThrow(()-> new RuntimeException("Resource Not Available"));
    }
}
