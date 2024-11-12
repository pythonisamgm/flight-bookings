package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.dtos.response.AuthResponse;
import com.flightbookings.flight_bookings.dtos.request.LoginRequest;
import com.flightbookings.flight_bookings.dtos.request.RegisterRequest;
import com.flightbookings.flight_bookings.models.UserEntity;
import com.flightbookings.flight_bookings.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for handling authentication operations such as login and registration.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;



    /**
     * Authenticates the user and returns an authentication response.
     *
     * @param login the login request containing username and password.
     * @return an AuthResponse containing the JWT token and user role.
     */
    public AuthResponse login(LoginRequest login) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        UserEntity user = iUserRepository.findByUsername(login.getUsername()).orElseThrow();

        String token = jwtService.getTokenService(user);

        return AuthResponse
                .builder()
                .token(token)
                .role(user.getRole())
                .build();
    }

    /**
     * Registers a new user and returns an authentication response.
     *
     * @param register the registration request containing user details.
     * @return an AuthResponse containing the JWT token and user role.
     */
    public AuthResponse register(RegisterRequest register) {
        UserEntity user = UserEntity.builder()
                .username(register.getUsername())
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .role(register.getRole())
                .build();

        iUserRepository.save(user);

        return AuthResponse
                .builder()
                .token(jwtService.getTokenService(user))
                .role(register.getRole())
                .build();
    }
}
