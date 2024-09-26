package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.request.LoginRequest;
import com.flightbookings.flight_bookings.dtos.request.RegisterRequest;
import com.flightbookings.flight_bookings.dtos.response.AuthResponse;
import com.flightbookings.flight_bookings.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }

}

