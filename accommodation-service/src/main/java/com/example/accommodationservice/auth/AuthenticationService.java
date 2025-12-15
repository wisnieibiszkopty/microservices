package com.example.accommodationservice.auth;

import com.example.accommodationservice.auth.dto.AuthenticationRequest;
import com.example.accommodationservice.auth.dto.AuthenticationResponse;
import com.example.accommodationservice.auth.dto.RegisterRequest;
import com.example.accommodationservice.user.User;
import com.example.accommodationservice.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    // TODO remove
    @Value("${jwt.expiration}")
    private Long expiration;


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private Map<String, Object> setClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("firstname", user.getFirstname());
        claims.put("lastname", user.getLastname());
        return claims;
    }

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        var existingUser = userRepository.findByEmail(request.email());

        if(existingUser.isPresent()){
            throw new Exception("User with this email already exists!");
        }

        var user = new User();
        user.setEmail(request.email());
        user.setFirstname(request.firstname());
        user.setLastname(request.lastname());
        user.setRole(request.role());

        var encodedPassword = passwordEncoder.encode(request.password());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        var claims = setClaims(user);

        var token = jwtService.generateToken(user, claims);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new AuthenticationResponse(
            token,
            refreshToken,
            "Bearer",
            // TODO replace with proper value
            LocalDateTime.now(),
            user.getEmail(),
            user.getRole()
        );
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var user = userRepository.findByEmailAndEnabledTrue(request.email());

        if(user.isEmpty()){
            throw new RuntimeException("User doesn't exist");
        }

        var existingUser = user.get();
        var claims = setClaims(existingUser);

        var token = jwtService.generateToken(existingUser, claims);
        var refreshToken = jwtService.generateRefreshToken(existingUser);

        return new AuthenticationResponse(
                token,
                refreshToken,
                "Bearer",
                // TODO replace with proper value
                LocalDateTime.now(),
                existingUser.getEmail(),
                existingUser.getRole()
        );
    }

    public AuthenticationResponse refreshToken(String authHeader){
        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt);

        var user = userRepository.findByEmail(userEmail);

        if(user.isEmpty()){
            throw new RuntimeException("Invalid user");
        }

        // TODO put generating token in one method
        var existingUser = user.get();
        var claims = setClaims(existingUser);

        var token = jwtService.generateToken(existingUser, claims);
        var refreshToken = jwtService.generateRefreshToken(existingUser);

        return new AuthenticationResponse(
                token,
                refreshToken,
                "Bearer",
                // TODO replace with proper value
                LocalDateTime.now(),
                existingUser.getEmail(),
                existingUser.getRole()
        );
    }
}
