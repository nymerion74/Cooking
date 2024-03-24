package com.example.cooking.controller;

import com.example.cooking.DTO.entry.LoginDto;
import com.example.cooking.DTO.mapper.UserMapper;
import com.example.cooking.DTO.response.UserDto;
import com.example.cooking.config.JwtTokenProvider;
import com.example.cooking.entity.User;
import com.example.cooking.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void registerUserShouldReturnUserDto() throws Exception {
        LoginDto loginDto = new LoginDto("testUser", "password");
        User user = new User();
        user.setUsername("testUser");
        UserDto userDto = new UserDto(1L, "testUser");

        when(userMapper.toEntity(any(LoginDto.class))).thenReturn(user);
        when(userService.registerUser(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        mockMvc.perform(post("/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(userDto.getUsername()));
    }

    @Test
    void loginUserShouldReturnToken() throws Exception {
        LoginDto loginDto = new LoginDto("testUser", "password");
        User user = new User();
        user.setUsername("testUser");
        UserDto userDto = new UserDto(1L, "testUser");

        when(userService.loginUser(any(LoginDto.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);
        when(jwtTokenProvider.createToken(eq("testUser"))).thenReturn("token");

        mockMvc.perform(post("/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", "Bearer token"))
                .andExpect(jsonPath("$.username").value(userDto.getUsername()));
    }

    @Test
    void getUserByIdShouldReturnUserDto() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        UserDto userDto = new UserDto(1L, "testUser");

        when(userService.getUserById(eq(1L))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        mockMvc.perform(get("/users/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userDto.getUsername()));
    }
}
