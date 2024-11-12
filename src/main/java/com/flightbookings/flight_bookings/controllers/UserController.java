package com.flightbookings.flight_bookings.controllers;


import com.flightbookings.flight_bookings.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightbookings.flight_bookings.models.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller for managing user-related operations such as creating, retrieving, updating, and deleting users.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/user")
@Tag(name = "User Management", description = "Operations pertaining to user management")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Creates a new user.
     *
     * @param user the user object to be created.
     * @return the created user.
     */
    @Operation(summary = "Create a new user")
    @PostMapping(value="/create",consumes = "application/json")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to be retrieved.
     * @return the user if found, otherwise 404 response.
     */
    @Operation(summary = "Get an user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@Parameter(description = "ID of the booking  to be retrieved") @PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    /**
     * Retrieves all users.
     *
     * @return the list of all users.
     */
    @Operation(summary = "Get all users")
    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    /**
     * Updates an existing user by their ID.
     *
     * @param id          the ID of the user to be updated.
     * @param userDetails the updated user details.
     * @return the updated user if found, otherwise 404 response.
     */
    @Operation(summary = "Update an user")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserEntity> updateUser(@Parameter(description = "ID of the booking  to be retrieved") @PathVariable Long id, @RequestBody UserEntity userDetails) {
        UserEntity updatedUser = userService.updateUser(id, userDetails);
        return updatedUser != null ? new ResponseEntity<>(updatedUser, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to be deleted.
     * @return a 204 response if deleted, otherwise 404.
     */
    @Operation(summary = "Deletes an user by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "ID of the booking  to be retrieved") @PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}