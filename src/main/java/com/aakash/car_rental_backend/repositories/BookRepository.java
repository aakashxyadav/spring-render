package com.aakash.car_rental_backend.repositories;

import com.aakash.car_rental_backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
