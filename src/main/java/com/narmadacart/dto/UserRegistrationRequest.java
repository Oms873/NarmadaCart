package com.narmadacart.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserRegistrationRequest {

    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    private String mobileNumber;
    private String role; // e.g., "USER" or "ADMIN"

}
