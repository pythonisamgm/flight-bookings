package com.flightbookings.flight_bookings.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
/**
 * Represents a user in the system.
 * Implements UserDetails for Spring Security authentication and authorization.
 * Contains user credentials, role, and associated bookings.
 */
@Entity
@Table(name = "user")
@Schema(description = "All details about the User entity.")
public class User implements UserDetails {

    /**
     * The unique identifier of the user generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The database generated user ID")
    private Long userId;

    /**
     * The username of the user.
     */
    @Column(nullable = false)
    @Schema(description = "The username of the user")
    private String username;

    /**
     * The password of the user.
     */
    @Column
    @Schema(description = "The password of the user")
    private String password;

    /**
     * The email address of the user.
     */
    @Column
    @Schema(description = "The email of the user")
    private String email;

    /**
     * The role assigned to the user.
     */
    @Enumerated(EnumType.STRING)
    @Schema(description = "The role of the user")
    private ERole role;

    /**
     * The list of bookings made by the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "user-booking")
    @Schema(description = "The list of bookings made by the user")
    private List<Booking> bookings;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Constructs a new User with the specified details.
     *
     * @param id        The user ID.
     * @param username  The username.
     * @param password  The password.
     * @param email     The email address.
     * @param role      The role of the user.
     * @param bookings  The list of bookings associated with the user.
     */
    public User(Long id, String username, String password, String email, ERole role, List<Booking> bookings) {
        this.userId = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.bookings = bookings;
    }

    /**
     * Gets the user ID.
     *
     * @return The user ID.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId The user ID.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the username.
     *
     * @return The username.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username The username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return The password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password The password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email address.
     *
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email The email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the role of the user.
     *
     * @return The role.
     */
    public ERole getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role The role.
     */
    public void setRole(ERole role) {
        this.role = role;
    }

    /**
     * Gets the list of bookings made by the user.
     *
     * @return The list of bookings.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Sets the list of bookings made by the user.
     *
     * @param bookings The list of bookings.
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return A collection of GrantedAuthority.
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return True if the account is non-expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return True if the account is non-locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     *
     * @return True if the credentials are non-expired, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return True if the user is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Creates a new Builder instance for the User class.
     *
     * @return A new Builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing User instances.
     */
    public static class Builder {
        private Long userId;
        private String username;
        private String email;
        private String password;
        private ERole role;
        private List<Booking> bookings;

        /**
         * Sets the user ID.
         *
         * @param id The user ID.
         * @return The current Builder instance.
         */
        public Builder userId(Long id) {
            this.userId = id;
            return this;
        }

        /**
         * Sets the username.
         *
         * @param username The username.
         * @return The current Builder instance.
         */
        public Builder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Sets the email address.
         *
         * @param email The email address.
         * @return The current Builder instance.
         */
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        /**
         * Sets the password.
         *
         * @param password The password.
         * @return The current Builder instance.
         */
        public Builder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * Sets the role of the user.
         *
         * @param role The role.
         * @return The current Builder instance.
         */
        public Builder role(ERole role) {
            this.role = role;
            return this;
        }

        /**
         * Sets the list of bookings.
         *
         * @param bookings The list of bookings.
         * @return The current Builder instance.
         */
        public Builder bookings(List<Booking> bookings) {
            this.bookings = bookings;
            return this;
        }

        /**
         * Builds and returns a User instance.
         *
         * @return A new User object.
         */
        public User build() {
            return new User(userId, username, password, email, role, bookings);
        }
    }
}