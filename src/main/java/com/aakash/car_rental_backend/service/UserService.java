package com.aakash.car_rental_backend.service;

import com.aakash.car_rental_backend.dto.AuthenticationRequest;
import com.aakash.car_rental_backend.dto.AuthenticationResponse;
import com.aakash.car_rental_backend.dto.RegisterRequestDto;
import com.aakash.car_rental_backend.dto.UserDto;
import com.aakash.car_rental_backend.entity.User;
import com.aakash.car_rental_backend.enums.UserRole;

import com.aakash.car_rental_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto registerUser(RegisterRequestDto request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setUserRole(request.getUserRole() != null ? request.getUserRole() : UserRole.CUSTOMER);
        user = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setUserRole(user.getUserRole());
        return userDto;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setName(user.getName());
                    userDto.setEmail(user.getEmail());
                    userDto.setUserRole(user.getUserRole());
                    return userDto;
                })
                .collect(Collectors.toList());
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        User user = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(request.getEmail()) && u.getPassword().equals(request.getPassword()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        AuthenticationResponse response = new AuthenticationResponse();
        response.setUserId(user.getId());
        response.setUserRole(user.getUserRole());
        String mockJwt = Base64.getEncoder().encodeToString((user.getEmail() + ":" + user.getId()).getBytes());
        response.setJwt(mockJwt);
        return response;
    }
}