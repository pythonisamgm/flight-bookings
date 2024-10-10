package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.request.LoginRequest;
import com.flightbookings.flight_bookings.dtos.request.RegisterRequest;
import com.flightbookings.flight_bookings.dtos.response.AuthResponse;
import com.flightbookings.flight_bookings.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Controller for handling authentication operations like login and registration.
 */
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    /**
     * Constructor to initialize the AuthController with AuthService.
     *
     * @param authService the authentication service.
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    /**
     * Logs in a user.
     *
     * @param request the login request containing username and password.
     * @return the authentication response with a token.
     */
    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }
    /**
     * Registers a new user.
     *
     * @param request the registration request containing username, email, password, and role.
     * @return the authentication response with a token.
     */
    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }

}

