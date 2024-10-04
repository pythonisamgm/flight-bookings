package com.flightbookings.flight_bookings.controllers;

import com.flightbookings.flight_bookings.dtos.DTOUser.UserConverter;
import com.flightbookings.flight_bookings.dtos.DTOUser.UserDTO;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @Operation(summary = "Create a new user")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = userConverter.dtoToUser(userDTO, new User());
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(userConverter.userToDto(createdUser), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@Parameter(description = "ID of the user to be retrieved") @PathVariable Long id) {
        Optional<User> userOptional = Optional.ofNullable(userService.getUserById(id));

        if (userOptional.isPresent()) {
            UserDTO userDTO = userConverter.userToDto(userOptional.get());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all users")
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(userConverter::userToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @Operation(summary = "Update a user")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@Parameter(description = "ID of the user to be updated") @PathVariable Long id, @RequestBody UserDTO userDTO) {
        Optional<User> existingUserOptional = Optional.ofNullable(userService.getUserById(id));

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            User user = userConverter.dtoToUser(userDTO, existingUser);
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(userConverter.userToDto(updatedUser));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Deletes a user by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "ID of the user to be deleted") @PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
