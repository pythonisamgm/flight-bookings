package com.flightbookings.flight_bookings.dtos.request;

import com.flightbookings.flight_bookings.models.ERole;

/**
 * Data Transfer Object (DTO) for handling registration requests.
 */
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private ERole role;

    /**
     * Constructs a RegisterRequest with the provided username, password, email, and role.
     *
     * @param username the username.
     * @param email    the user's email.
     * @param password the password.
     * @param role     the user's role.
     */
    public RegisterRequest(String username, String email, String password, ERole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public RegisterRequest build() {
        return new RegisterRequest(username, email, password, role);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing RegisterRequest objects.
     */
    public static class Builder {
        private String username;
        private String password;
        private String email;
        private ERole role;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder role(ERole role) {
            this.role = role;
            return this;
        }
    }
}
