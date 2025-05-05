package com.aakash.car_rental_backend.repositories;

import com.aakash.car_rental_backend.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Long> {
    @Query("SELECT c FROM Car c WHERE " +
            "(:brand IS NULL OR c.brand = :brand) AND " +
            "(:type IS NULL OR c.type = :type) AND " +
            "(:transmission IS NULL OR c.transmission = :transmission) AND " +
            "(:color IS NULL OR c.color = :color)")
    List<Car> searchCars(@Param("brand") String brand,
                         @Param("type") String type,
                         @Param("transmission") String transmission,
                         @Param("color") String color);
}
