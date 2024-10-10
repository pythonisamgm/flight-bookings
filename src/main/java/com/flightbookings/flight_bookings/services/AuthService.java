package com.flightbookings.flight_bookings.services;

import com.flightbookings.flight_bookings.dtos.response.AuthResponse;
import com.flightbookings.flight_bookings.dtos.request.LoginRequest;
import com.flightbookings.flight_bookings.dtos.request.RegisterRequest;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.repositories.IUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for handling authentication operations such as login and registration.
 */
@Service
public class AuthService {

    private final JwtService jwtService;
    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructs an AuthService with the required dependencies.
     *
     * @param jwtService           the service for generating JWT tokens.
     * @param iUserRepository      the repository for managing users.
     * @param passwordEncoder      the encoder for user passwords.
     * @param authenticationManager the manager for handling authentication.
     */
    public AuthService(JwtService jwtService, IUserRepository iUserRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.iUserRepository = iUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authenticates the user and returns an authentication response.
     *
     * @param login the login request containing username and password.
     * @return an AuthResponse containing the JWT token and user role.
     */
    public AuthResponse login(LoginRequest login) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        User user = iUserRepository.findByUsername(login.getUsername()).orElseThrow();

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
        User user = User.builder()
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
