package com.aakash.car_rental_backend.controller;

import com.aakash.car_rental_backend.dto.BookDto;
import com.aakash.car_rental_backend.dto.CarDto;
import com.aakash.car_rental_backend.dto.UserDto;
import com.aakash.car_rental_backend.dto.CarListDto;
import com.aakash.car_rental_backend.service.AdminService;
import com.aakash.car_rental_backend.service.BookService;
import com.aakash.car_rental_backend.service.CarService;
import com.aakash.car_rental_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final CarService carService;
    private final BookService bookingService;
    @Autowired
    private  final AdminService adminService;


    private final UserService userService;

    public AdminController(CarService carService, BookService bookingService, AdminService adminService, UserService userService) {
        this.carService = carService;
        this.bookingService = bookingService;
        this.adminService = adminService;
        this.userService = userService;
    }

    @PostMapping("/add-car")
    public ResponseEntity<CarDto> addCar(@ModelAttribute CarDto carDto) throws IOException {
        // Simple role check (no security)
        if (carDto.getBrand() == null) {
            throw new RuntimeException("Car brand is required");
        }
        CarDto addedCar = carService.addCar(carDto);
        return ResponseEntity.ok(addedCar);
    }

    @PutMapping("/bookings/{id}")
    public ResponseEntity<BookDto> updateBookingStatus(@PathVariable Long id, @RequestBody BookDto bookDto) {
        // Simple role check (no security)
        if (bookDto.getBookingStatus() == null) {
            throw new RuntimeException("Booking status is required");
        }
        BookDto updatedBooking = bookingService.updateBookingStatus(id, bookDto);
        return ResponseEntity.ok(updatedBooking);
    }
    @GetMapping("/car/booking/{bookingId}/{status}")
    public ResponseEntity<?> changeBookingStatus(@PathVariable Long bookingId, @PathVariable String status) {
        boolean success = adminService.changeBookingStatus(bookingId, status);
        if (success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/cars")
    public ResponseEntity<CarListDto> getAllCars() {
        CarListDto carListDto = carService.getAllCars();
        return ResponseEntity.ok(carListDto);
    }
}
