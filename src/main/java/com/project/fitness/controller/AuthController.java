package com.project.fitness.controller;

import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user") //starting point of url
@RequiredArgsConstructor //only generates final declared instances
public class AuthController {

    private final UserService userService; // we have to declare a contsructor for dependency injection as we are doing constructor DI

//    public AuthController(UserService userService) { //spring managed component and we need one instance of UserService in this controller
//        this.userService = userService;
//    } WE USE LONBOK

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.register(registerRequest)); //sending status code 200
    }
}
