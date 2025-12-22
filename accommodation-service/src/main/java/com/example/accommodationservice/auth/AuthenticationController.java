package com.example.accommodationservice.auth;

import com.example.accommodationservice.auth.dto.AuthenticationRequest;
import com.example.accommodationservice.auth.dto.AuthenticationResponse;
import com.example.accommodationservice.auth.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest request) throws Exception {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("/refresh-token")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @RequestHeader("Authorization") String authHeader
    ){
        return ResponseEntity.ok(authService.refreshToken(authHeader));
    }
}
