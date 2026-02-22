package com.project.fitness.service;

import com.project.fitness.dto.LoginRequest;
import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.model.User;
import com.project.fitness.model.UserRole;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest request) {
//        User user = new User(request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName());

        UserRole role = request.getRole() != null ? request.getRole()
                : UserRole.USER;
        User user = User.builder() // can be done using constructors as well but this is more readable and constructor causes tight coupliing
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build(); //done using builder
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    public UserResponse mapToResponse(User savedUser) { // accepting usesr type and converint to userresponse type
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setPassword(savedUser.getPassword());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());
        return response;
    }

    public User authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null) throw new RuntimeException(("invalid credentials, user not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException(("invalid credentials - password mismatch"));
        }
        System.out.println("user details for login is - "+ user);
        return user;
    }
}
