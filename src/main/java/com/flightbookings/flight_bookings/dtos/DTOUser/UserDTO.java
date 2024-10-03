package com.flightbookings.flight_bookings.dtos.DTOUser;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Data Transfer Object for User.")
public class UserDTO {

    @Schema(description = "The database generated user ID")
    private Long userId;

    @Schema(description = "The username of the user")
    private String username;

    @Schema(description = "The email of the user")
    private String email;

    @Schema(description = "The role of the user")
    private String role;

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
