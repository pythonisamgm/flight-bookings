package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * Repository interface for managing {@link User} entities.
 * Provides CRUD operations and a method to find a user by username.
 */
public interface IUserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find.
     * @return an optional containing the found user, or empty if not found.
     */
    Optional<User> findByUsername(String username);
}
