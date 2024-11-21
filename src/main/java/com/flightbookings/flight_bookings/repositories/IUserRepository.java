package com.flightbookings.flight_bookings.repositories;

import com.flightbookings.flight_bookings.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Repository interface for managing {@link UserEntity} entities.
 * Provides CRUD operations and a method to find a user by username.
 */
@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find.
     * @return an optional containing the found user, or empty if not found.
     */
    Optional<UserEntity> findByUsername(String username);
}
