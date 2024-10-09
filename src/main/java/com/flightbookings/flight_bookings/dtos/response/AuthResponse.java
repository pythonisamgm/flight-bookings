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

    /**
     * @return the JWT token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the JWT token.
     *
     * @param token the token to set.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the role of the user.
     */
    public ERole getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the role to set.
     */
    public void setRole(ERole role) {
        this.role = role;
    }

    /**
     * Builder pattern for creating AuthResponse objects.
     *
     * @return a new Builder instance.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing AuthResponse objects.
     */
    public static class Builder {
        private String token;
        private ERole role;

        /**
         * Sets the token for the AuthResponse.
         *
         * @param token the JWT token to set.
         * @return the Builder instance.
         */
        public Builder token(String token) {
            this.token = token;
            return this;
        }

        /**
         * Sets the role for the AuthResponse.
         *
         * @param role the role to set.
         * @return the Builder instance.
         */
        public Builder role(ERole role) {
            this.role = role;
            return this;
        }

        /**
         * Builds and returns the AuthResponse object.
         *
         * @return the constructed AuthResponse.
         */
        public AuthResponse build() {
            return new AuthResponse(token, role);
        }
    }
}
