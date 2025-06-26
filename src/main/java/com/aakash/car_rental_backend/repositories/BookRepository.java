package com.aakash.car_rental_backend.repositories;

import com.aakash.car_rental_backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findByUserId(Long userId);
}
