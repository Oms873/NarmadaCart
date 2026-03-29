package com.narmadacart.controller;

import com.narmadacart.dto.UserLoginRequest;
import com.narmadacart.dto.UserRegistrationRequest;
import com.narmadacart.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService authService;

    public AuthController(UserService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        return authService.registerUser(request);
    }

    //1. For Role User: hellouser123@gmail.com / User@1234
    //2. For Role Merchant: hellomerchant123@gmail.com / Merchant@1234

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request) {
         System.out.println("Login attempt for email: " + request.getEmail());
        return authService.loginUser(request);
    }

}
