package com.aakash.car_rental_backend.dto;

import lombok.Data;

@Data
public class SearchDto {
    private String brand;
    private String type;
    private String transmission;
    private String color;
}
