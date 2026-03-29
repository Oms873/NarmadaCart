package com.narmadacart.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.narmadacart.config.JwtUtil;
import com.narmadacart.controller.AuthController;
import com.narmadacart.dto.UserRegistrationRequest;
import com.narmadacart.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    JwtUtil jwtUtil;

    @Test
    void testRegisterUser_Success() throws Exception{
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setName("test");
        request.setEmail("test@gmail.com");
        request.setPassword("Test@123");
        request.setMobileNumber("1234567890");
        request.setRole("USER");

        Mockito.when(userService.registerUser(any(UserRegistrationRequest.class)))
                .thenReturn(ResponseEntity.ok("User registered successfully"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    void testRegisterUser_EmailAlreadyInUse() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setName("test");
        request.setEmail("test@gmail.com");
        request.setPassword("Test@123");
        request.setMobileNumber("1234567890");
        request.setRole("USER");

        Mockito.when(userService.registerUser(any(UserRegistrationRequest.class)))
                .thenReturn(ResponseEntity.badRequest().body("Email is already in use"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email is already in use"));
    }
}
