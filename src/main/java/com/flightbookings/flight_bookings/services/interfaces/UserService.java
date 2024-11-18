package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.User;

import java.util.List;

/**
 * Interface for managing user operations in the flight booking system.
 */
public interface UserService {

    /**
     * Creates a new user.
     *
     * @param user the User object to create.
     * @return the created User object.
     */
    User createUser(User user);

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve.
     * @return the User object if found, null otherwise.
     */
    User getUserById(Long id);

    /**
     * Retrieves all users.
     *
     * @return a list of all User objects.
     */
    List<User> getAllUsers();

    /**
     * Updates a user's details.
     *
     * @param id          the ID of the user to update.
     * @param userDetails the updated User object.
     * @return the updated User object.
     */
    User updateUser(Long id, User userDetails);

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete.
     * @return true if the user was successfully deleted, false otherwise.
     */
    boolean deleteUser(Long id);

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find.
     * @return the User object if found, throws an exception if not found.
     */
    User findByUsername(String username);
}
