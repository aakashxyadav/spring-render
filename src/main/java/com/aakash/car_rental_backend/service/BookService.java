package com.aakash.car_rental_backend.service;

import com.aakash.car_rental_backend.dto.BookDto;
import com.aakash.car_rental_backend.entity.Book;
import com.aakash.car_rental_backend.entity.Car;
import com.aakash.car_rental_backend.entity.User;
import com.aakash.car_rental_backend.enums.BookingStatus;
import com.aakash.car_rental_backend.repositories.BookRepository;
import com.aakash.car_rental_backend.repositories.CarRepository;
import com.aakash.car_rental_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookingRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public BookService(BookRepository bookingRepository, UserRepository userRepository, CarRepository carRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    public BookDto createBooking(BookDto bookDto) {
        User user = userRepository.findById(bookDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Car car = carRepository.findById(bookDto.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));

        Book booking = new Book();
        booking.setFromDate(bookDto.getFromDate());
        booking.setToDate(bookDto.getToDate());
        booking.setDays(bookDto.getDays());
        booking.setPrice(bookDto.getPrice());
        booking.setBookingStatus(bookDto.getBookingStatus() != null ? bookDto.getBookingStatus() : BookingStatus.PENDING);
        booking.setUser(user);
        booking.setCar(car);

        booking = bookingRepository.save(booking);

        return convertToDto(booking);
    }

    public BookDto updateBookingStatus(Long id, BookDto bookDto) {
        Book booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setBookingStatus(bookDto.getBookingStatus());
        booking = bookingRepository.save(booking);
        return convertToDto(booking);
    }

    public List<BookDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private BookDto convertToDto(Book booking) {
        BookDto bookDto = new BookDto();
        bookDto.setId(booking.getId());
        bookDto.setFromDate(booking.getFromDate());
        bookDto.setToDate(booking.getToDate());
        bookDto.setDays(booking.getDays());
        bookDto.setPrice(booking.getPrice());
        bookDto.setBookingStatus(booking.getBookingStatus());
        bookDto.setUserId(booking.getUser().getId());
        bookDto.setCarId(booking.getCar().getId());
        bookDto.setUsername(booking.getUser().getName());
        bookDto.setEmail(booking.getUser().getEmail());
        return bookDto;
    }
}

