package com.aakash.car_rental_backend.service;

import com.aakash.car_rental_backend.dto.CarDto;
import com.aakash.car_rental_backend.dto.CarListDto;
import com.aakash.car_rental_backend.dto.SearchDto;
import com.aakash.car_rental_backend.entity.Car;
import com.aakash.car_rental_backend.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public CarListDto getAllCars() {
        List<CarDto> carDtos = carRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        CarListDto carListDto = new CarListDto();
        carListDto.setCarDtoList(carDtos);
        return carListDto;
    }

    public CarListDto searchCars(SearchDto searchDto) {
        String brand = normalize(searchDto.getBrand());
        String type = normalize(searchDto.getType());
        String transmission = normalize(searchDto.getTransmission());
        String color = normalize(searchDto.getColor());

        List<Car> cars = carRepository.searchCars(brand, type, transmission, color);
        List<CarDto> carDtos = cars.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        CarListDto carListDto = new CarListDto();
        carListDto.setCarDtoList(carDtos);
        return carListDto;
    }
    public CarDto getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with ID: " + id));
        return convertToDto(car);  // assuming you already have a convertToDto method
    }


    private String normalize(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value.trim();
    }


    public CarDto addCar(CarDto carDto) throws IOException {
        Car car = new Car();
        car.setBrand(carDto.getBrand());
        car.setColor(carDto.getColor());
        car.setName(carDto.getName());
        car.setType(carDto.getType());
        car.setTransmission(carDto.getTransmission());
        car.setDescription(carDto.getDescription());
        car.setPrice(carDto.getPrice());
        car.setYear(carDto.getYear());
        if (carDto.getImage() != null && !carDto.getImage().isEmpty()) {
            car.setImage(carDto.getImage().getBytes());
        }
        car = carRepository.save(car);
        return convertToDto(car);
    }

    public void initializeCars() {
        Car car1 = new Car();
        car1.setBrand("Toyota");
        car1.setColor("Red");
        car1.setName("Corolla");
        car1.setType("Sedan");
        car1.setTransmission("Automatic");
        car1.setDescription("Reliable sedan");
        car1.setPrice(50L);
        car1.setYear("2020");

        Car car2 = new Car();
        car2.setBrand("Honda");
        car2.setColor("Black");
        car2.setName("Civic");
        car2.setType("Sedan");
        car2.setTransmission("Manual");
        car2.setDescription("Sporty sedan");
        car2.setPrice(60L);
        car2.setYear("2021");

        carRepository.saveAll(List.of(car1, car2));
    }

    private CarDto convertToDto(Car car) {
        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setBrand(car.getBrand());
        carDto.setColor(car.getColor());
        carDto.setName(car.getName());
        carDto.setType(car.getType());
        carDto.setTransmission(car.getTransmission());
        carDto.setDescription(car.getDescription());
        carDto.setPrice(car.getPrice());
        carDto.setYear(car.getYear());
        carDto.setReturnedImg(car.getImage());
        return carDto;
    }
}