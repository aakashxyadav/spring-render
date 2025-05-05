package com.aakash.car_rental_backend.config;

import com.aakash.car_rental_backend.entity.User;
import com.aakash.car_rental_backend.enums.UserRole;
import com.aakash.car_rental_backend.repositories.UserRepository;
import com.aakash.car_rental_backend.service.CarService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(CarService carService, UserRepository userRepository) {
        return args -> {
            // Initialize sample cars
            carService.initializeCars();

            // Initialize sample admin user
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@example.com");
            admin.setPassword("admin123");
            admin.setUserRole(UserRole.ADMIN);
            userRepository.save(admin);
        };
    }
}
