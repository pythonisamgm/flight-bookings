package com.flightbookings.flight_bookings.dtos.request;

/**
 * DTO representing a login request containing username and password.
 */
public class LoginRequest {
    private String username;
    private String password;

    /**
     * Constructs a new LoginRequest with the provided username and password.
     *
     * @param username the username.
     * @param password the password.
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Default constructor for LoginRequest.
     */
    public LoginRequest() {}

    /**
     * Gets the username.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Builds a new LoginRequest using the current data.
     *
     * @return a new LoginRequest instance.
     */
    public LoginRequest build() {
        return new LoginRequest(username, password);
    }

    /**
     * Returns a new Builder instance for LoginRequest.
     *
     * @return a Builder for LoginRequest.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for LoginRequest.
     */
    public static class Builder {
        private String username;
        private String password;

        /**
         * Sets the username for the LoginRequest.
         *
         * @param username the username.
         * @return the Builder instance.
         */
        public Builder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Sets the password for the LoginRequest.
         *
         * @param password the password.
         * @return the Builder instance.
         */
        public Builder password(String password) {
            this.password = password;
            return this;
        }
    }
}
