package com.flightbookings.flight_bookings.dtos.DTOUser;

import com.flightbookings.flight_bookings.dtos.DTOBooking.BookingDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * Data Transfer Object for User.
 * This class represents the data that will be transferred between the client and server
 * related to the User entity.
 */
@Schema(description = "Data Transfer Object for User.")
public class UserDTO {

    @Schema(description = "The database generated user ID")
    private Long userId;

    @NotBlank(message = "Username cannot be empty")
    @Schema(description = "The username of the user")
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    @Schema(description = "The email of the user")
    private String email;

    @Schema(description = "The role of the user")
    private String role;

    @Schema(description = "The list of bookings made by the user")
    private List<BookingDTO> bookings;

    /**
     * Default constructor for UserDTO.
     */
    public UserDTO() {}

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the user ID to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the username.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the role of the user.
     *
     * @return the role of the user
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the list of bookings made by the user.
     *
     * @return the list of bookings
     */
    public List<BookingDTO> getBookings() {
        return bookings;
    }

    /**
     * Sets the list of bookings made by the user.
     *
     * @param bookings the list of bookings to set
     */
    public void setBookings(List<BookingDTO> bookings) {
        this.bookings = bookings;
    }
}
