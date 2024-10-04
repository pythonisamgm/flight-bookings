package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOUser.UserDTO;
import com.flightbookings.flight_bookings.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flightbookings.flight_bookings.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for managing user-related operations such as creating, retrieving, updating, and deleting users.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/user")
@Tag(name = "User Management", description = "Operations pertaining to user management")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user")
    @PostMapping(value="/create", consumes = "application/json")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        User newUser = userService.createUser(user);
        UserDTO newUserDTO = new UserDTO(newUser.getUserId(), newUser.getUsername(), newUser.getEmail());
        return new ResponseEntity<>(newUserDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@Parameter(description = "ID of the user to be retrieved") @PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            UserDTO userDTO = new UserDTO(user.getUserId(), user.getUsername(), user.getEmail());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all users")
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(user -> new UserDTO(user.getUserId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Update a user")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@Parameter(description = "ID of the user to be updated") @PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUserId(id); // Usa setUserId() en lugar de setId()
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // Asegúrate de establecer la contraseña

        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            UserDTO updatedUserDTO = new UserDTO(updatedUser.getUserId(), updatedUser.getUsername(), updatedUser.getEmail());
            return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deletes a user by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "ID of the user to be deleted") @PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
