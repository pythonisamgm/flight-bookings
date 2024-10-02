package com.flightbookings.flight_bookings.dtos.response;

import com.flightbookings.flight_bookings.models.ERole;

/**
 * Data Transfer Object (DTO) for returning authentication response data.
 */
public class AuthResponse {
    private ERole role;
    private String token;

    /**
     * Constructs an AuthResponse with the given token and role.
     *
     * @param token the JWT token.
     * @param role  the user's role.
     */
    public AuthResponse(String token, ERole role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing AuthResponse objects.
     */
    public static class Builder {
        private String token;
        private ERole role;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder role(ERole role) {
            this.role = role;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(token, role);
        }
    }
}
