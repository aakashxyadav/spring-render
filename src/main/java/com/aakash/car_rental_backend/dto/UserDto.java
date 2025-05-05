package com.aakash.car_rental_backend.dto;

import com.aakash.car_rental_backend.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private UserRole userRole;
}