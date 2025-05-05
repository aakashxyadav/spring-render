package com.aakash.car_rental_backend.service;

import com.aakash.car_rental_backend.dto.BookDto;
import com.aakash.car_rental_backend.dto.CarDto;
import com.aakash.car_rental_backend.dto.CarListDto;
import com.aakash.car_rental_backend.dto.SearchDto;
import com.aakash.car_rental_backend.entity.Book;
import com.aakash.car_rental_backend.entity.Car;
import com.aakash.car_rental_backend.enums.BookingStatus;
import com.aakash.car_rental_backend.repositories.BookRepository;
import com.aakash.car_rental_backend.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService  {

    private final CarRepository carRepository;
    private final BookRepository bookRepository;


    public boolean addCar(CarDto carDto) throws IOException {
        try {
            Car car = new Car();
            car.setName(carDto.getName());
            car.setBrand(carDto.getBrand());
            car.setColor(carDto.getColor());
            car.setPrice(carDto.getPrice());
            car.setYear(carDto.getYear());
            car.setType(carDto.getType());
            car.setDescription(carDto.getDescription());
            car.setTransmission(carDto.getTransmission());
            car.setImage(carDto.getImage().getBytes());
            carRepository.save(car);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }


    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }


    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }


    public CarDto getCarById(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }


    public boolean updateCar(Long carId, CarDto carDto) throws IOException {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isPresent()) {
            Car existedCar = optionalCar.get();
            if (carDto.getImage() != null)
                existedCar.setImage(carDto.getImage().getBytes());
            existedCar.setPrice(carDto.getPrice());
            existedCar.setYear(carDto.getYear());
            existedCar.setColor(carDto.getColor());
            existedCar.setTransmission(carDto.getTransmission());
            existedCar.setType(carDto.getType());
            existedCar.setDescription(carDto.getDescription());
            existedCar.setName(carDto.getName());
            existedCar.setBrand(carDto.getBrand());
            carRepository.save(existedCar);
            return true;
        }else {
            return false;
        }
    }





    public boolean changeBookingStatus(Long bookingId, String status) {
        Optional<Book> optionalBook = bookRepository.findById(bookingId);
        if (optionalBook.isPresent()){
            Book existingBook = optionalBook.get();
            if (Objects.equals(status, "APPROVED"))
                existingBook.setBookingStatus(BookingStatus.APPROVED);
            else
                existingBook.setBookingStatus(BookingStatus.REJECTED);
            bookRepository.save(existingBook);
            return true;
        }
        return false;
    }


    public CarListDto searchCar(SearchDto searchDto) {
        Car car = new Car();
        car.setBrand(searchDto.getBrand());
        car.setType(searchDto.getType());
        car.setTransmission(searchDto.getTransmission());
        car.setColor(searchDto.getColor());
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("brand",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("type",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("transmission",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("color",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<Car> carExample = Example.of(car, exampleMatcher);
        List<Car> carList = carRepository.findAll(carExample);
        CarListDto carListDto = new CarListDto();
        carListDto.setCarDtoList(carList.stream().map(Car::getCarDto).collect(Collectors.toList()));
        return carListDto;
    }
}