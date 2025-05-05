package com.aakash.car_rental_backend.repositories;

import com.aakash.car_rental_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
