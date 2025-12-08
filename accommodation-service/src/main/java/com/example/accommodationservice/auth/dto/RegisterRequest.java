package com.example.accommodationservice.auth.dto;

import com.example.accommodationservice.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank
        @Min(2)
        @Max(50)
        String firstname,
        @NotBlank
        @Min(2)
        @Max(50)
        String lastname,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        @NotBlank
        UserRole role
) {}
