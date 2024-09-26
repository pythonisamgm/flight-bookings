package com.flightbookings.flight_bookings.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightbookings.flight_bookings.models.ERole;
import com.flightbookings.flight_bookings.models.User;
import com.flightbookings.flight_bookings.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private User user1;
    private User user2;
    private List<User> userList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user1 = User.builder()
                .id(1L)
                .username("juanantonio")
                .password("1234")
                .email("jantonio@gmail.com")
                .role(ERole.ADMIN)
                .bookings(new ArrayList<>())
                .build();

        user2 = User.builder()
                .id(2L)
                .username("miguelangel")
                .password("12345")
                .email("mangel@gmail.com")
                .role(ERole.USER)
                .bookings(new ArrayList<>())
                .build();

        userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
    }

    @Test
    public void testCreateUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user1);

        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("juanantonio"))
                .andExpect(jsonPath("$.password").value("1234"))
                .andExpect(jsonPath("$.email").value("jantonio@gmail.com"));

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    public void testGetUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user1);

        mockMvc.perform(get("/api/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/api/user/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void testUpdateUser() throws Exception {

        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(user1);

        mockMvc.perform(put("/api/user/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(userService, times(1)).updateUser(eq(1L), any(User.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/user/delete/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }
}