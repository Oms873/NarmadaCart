package com.narmadacart.service;

import com.narmadacart.config.JwtUtil;
import com.narmadacart.dto.JwtResponse;
import com.narmadacart.dto.UserLoginRequest;
import com.narmadacart.dto.UserRegistrationRequest;
import com.narmadacart.entity.Role;
import com.narmadacart.entity.User;
import com.narmadacart.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        System.out.println("Userservice is created");
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> registerUser(UserRegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            Role role = userRepository.findByEmail(request.getEmail()).get().getRole();
            System.out.println("Existing user role: " + role);
            return ResponseEntity.badRequest().body("Email is already in use");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setMobileNumber(request.getMobileNumber());
        user.setRole(Role.valueOf(request.getRole()));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<?> loginUser(UserLoginRequest request) {
        System.out.println("Attempting to authenticate user: " + request.getEmail());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            return  ResponseEntity.status(401).body(e.getMessage());
        }
        System.out.println("Attempting to authenticate user 5: " + request.getEmail());
        User user = userRepository.findByEmail(request.getEmail()).get();
        String token = jwtUtil.generateToken(user);
        System.out.println(token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
