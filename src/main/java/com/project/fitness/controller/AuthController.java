package com.project.fitness.controller;

import com.project.fitness.dto.LoginRequest;
import com.project.fitness.dto.LoginResponse;
import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.model.User;
import com.project.fitness.repository.UserRepository;
import com.project.fitness.security.JwtUtils;
import com.project.fitness.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth") //starting point of url
@RequiredArgsConstructor //only generates final declared instances
public class AuthController {

    private final UserService userService; // we have to declare a contsructor for dependency injection as we are doing constructor DI
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

//    public AuthController(UserService userService) { //spring managed component and we need one instance of UserService in this controller
//        this.userService = userService;
//    } WE USE LONBOK

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> Login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {

            User user = userService.authenticate(loginRequest);

            String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name()); //it is a enum so we add .getname()
            LoginResponse loginCreds = new LoginResponse(token, userService.mapToResponse(user));
            System.out.println(ResponseEntity.ok(loginCreds));
            return ResponseEntity.ok(loginCreds);

        }catch (AuthenticationException e){
            e.printStackTrace();
            System.out.println(e);
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.register(registerRequest)); //sending status code 200
    }


}
