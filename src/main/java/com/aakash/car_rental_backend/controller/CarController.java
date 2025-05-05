package com.aakash.car_rental_backend.controller;

import com.aakash.car_rental_backend.dto.CarDto;
import com.aakash.car_rental_backend.dto.CarListDto;
import com.aakash.car_rental_backend.dto.SearchDto;
import com.aakash.car_rental_backend.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        CarDto carDto = carService.getCarById(id);
        return ResponseEntity.ok(carDto);
    }

    @GetMapping
    public ResponseEntity<CarListDto> getAllCars() {
        CarListDto carListDto = carService.getAllCars();
        return ResponseEntity.ok(carListDto);
    }

    @PostMapping("/search")
    public ResponseEntity<CarListDto> searchCars(@RequestBody SearchDto searchDto) {
        CarListDto carListDto = carService.searchCars(searchDto);
        return ResponseEntity.ok(carListDto);
    }
}