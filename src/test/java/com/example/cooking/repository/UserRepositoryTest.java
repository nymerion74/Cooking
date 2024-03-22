package com.example.cooking.repository;

import com.example.cooking.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsernameShouldReturnUser() {
        // Given
        User newUser = new User();
        newUser.setUsername("testUser");
        newUser.setPassword("password");
        userRepository.save(newUser);

        // When
        Optional<User> foundUser = userRepository.findByUsername("testUser");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testUser");
    }

    @Test
    public void findByUsernameShouldReturnEmptyIfUserNotFound() {
        // Given
        String username = "nonexistentUser";

        // When
        Optional<User> foundUser = userRepository.findByUsername(username);

        // Then
        assertThat(foundUser).isNotPresent();
    }
}
