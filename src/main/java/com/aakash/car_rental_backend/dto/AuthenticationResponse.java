package com.aakash.car_rental_backend.dto;



import com.aakash.car_rental_backend.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private UserRole userRole;
    private Long userId;
}