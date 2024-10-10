package com.flightbookings.flight_bookings.dtos.DTOUser;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
/**
 * Data Transfer Object for User.
 */
@Schema(description = "Data Transfer Object for User.")
public class UserDTO {
    /**
     * The unique identifier of the user.
     */
    @Schema(description = "The database generated user ID")
    private Long userId;
    /**
     * The username of the user.
     */
    @Schema(description = "The username of the user")
    private String username;
    /**
     * The email of the user.
     */
    @Schema(description = "The email of the user")
    private String email;
    /**
     * The role of the user.
     */
    @Schema(description = "The role of the user")
    private String role;
    /**
     * List of booking IDs made by the user.
     */
    @Schema(description = "The list of bookings made by the user")
    private List<Long> bookingIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Long> getBookingIds() {
        return bookingIds;
    }

    public void setBookingIds(List<Long> bookingIds) {
        this.bookingIds = bookingIds;
    }
}
