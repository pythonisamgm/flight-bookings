package com.flightbookings.flight_bookings.services.interfaces;

import com.flightbookings.flight_bookings.models.UserEntity;

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
    UserEntity createUser(UserEntity user);

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve.
     * @return the User object if found, null otherwise.
     */
    UserEntity getUserById(Long id);

    /**
     * Retrieves all users.
     *
     * @return a list of all User objects.
     */
    List<UserEntity> getAllUsers();

    /**
     * Updates a user's details.
     *
     * @param id          the ID of the user to update.
     * @param userDetails the updated User object.
     * @return the updated User object.
     */
    UserEntity updateUser(Long id, UserEntity userDetails);

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
    UserEntity findByUsername(String username);
}
