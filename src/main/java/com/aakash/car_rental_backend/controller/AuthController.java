package com.aakash.car_rental_backend.controller;

import com.aakash.car_rental_backend.dto.AuthenticationRequest;
import com.aakash.car_rental_backend.dto.AuthenticationResponse;
import com.aakash.car_rental_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}