package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOUser.UserDTO;
import com.flightbookings.flight_bookings.dtos.DTOUser.UserConverter;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/user")
@Tag(name = "User Management", description = "Operations pertaining to user management")
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;

    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    /**
     * Creates a new user in the system.
     *
     * @param userDTO the user data transfer object containing user information
     * @return ResponseEntity containing the created user data and HTTP status code
     */
    @Operation(summary = "Create a new user")
    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userConverter.dtoToUser(userDTO, null);
        User newUser = userService.createUser(user);
        UserDTO newUserDTO = userConverter.userToDto(newUser);
        return new ResponseEntity<>(newUserDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to be retrieved
     * @return ResponseEntity containing the user data and HTTP status code
     */
    @Operation(summary = "Get a user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "ID of the user to be retrieved") @PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ?
                new ResponseEntity<>(userConverter.userToDto(user), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves all users in the system.
     *
     * @return ResponseEntity containing a list of user data and HTTP status code
     */
    @Operation(summary = "Get all users")
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(userConverter::userToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    /**
     * Updates an existing user's information.
     *
     * @param id the ID of the user to be updated
     * @param userDTO the user data transfer object containing updated user information
     * @return ResponseEntity containing the updated user data and HTTP status code
     */
    @Operation(summary = "Update a user")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @Parameter(description = "ID of the user to be updated") @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        User user = userConverter.dtoToUser(userDTO, userService.getUserById(id));
        User updatedUser = userService.updateUser(id, user);
        return updatedUser != null ?
                new ResponseEntity<>(userConverter.userToDto(updatedUser), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes a user from the system by their ID.
     *
     * @param id the ID of the user to be deleted
     * @return ResponseEntity with HTTP status code
     */
    @Operation(summary = "Deletes a user by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to be deleted") @PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
