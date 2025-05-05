package com.aakash.car_rental_backend.controller;

import com.aakash.car_rental_backend.dto.BookDto;
import com.aakash.car_rental_backend.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/")
public class BookController {

    private final BookService bookingService;

    public BookController(BookService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("book-car")
    public ResponseEntity<BookDto> createBooking(@RequestBody BookDto bookDto) {
        BookDto createdBooking = bookingService.createBooking(bookDto);
        return ResponseEntity.ok(createdBooking);
    }

    @GetMapping("allbookings")
    public ResponseEntity<List<BookDto>> getAllBookings() {
        List<BookDto> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
}