package com.aakash.car_rental_backend.controller;

import com.aakash.car_rental_backend.dto.RegisterRequestDto;
import com.aakash.car_rental_backend.dto.UserDto;
import com.aakash.car_rental_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequestDto request) {
        UserDto userDto = userService.registerUser(request);
        return ResponseEntity.ok(userDto);
    }
}