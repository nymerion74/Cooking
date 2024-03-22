package com.example.cooking.service;

import com.example.cooking.DTO.LoginDto;
import com.example.cooking.entity.User;
import com.example.cooking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("password");
    }

    @Test
    void shouldRegisterUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.registerUser(user);

        assertThat(savedUser).isNotNull();
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldLoginUser() throws Exception {
        LoginDto dto = new LoginDto(user.getUsername(), user.getPassword());
        when(userRepository.findUserByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(Optional.ofNullable(user));
        User loggedInUser = userService.loginUser(dto);

        assertThat(loggedInUser).isEqualTo(user);
    }

    @Test
    void shouldGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);

        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(userId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found with id: " + userId);
    }
}
