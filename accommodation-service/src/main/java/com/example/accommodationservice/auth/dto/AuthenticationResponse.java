package com.example.accommodationservice.auth.dto;

import com.example.accommodationservice.user.UserRole;

import java.time.LocalDateTime;

public record AuthenticationResponse(
   String token,
   String refreshToken,
   String tokenType,
   LocalDateTime expiresAt,
   String userEmail,
   UserRole userRole
) {}
