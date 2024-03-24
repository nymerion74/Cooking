package com.example.cooking.service;

import com.example.cooking.DTO.entry.LoginDto;
import com.example.cooking.entity.User;
import com.example.cooking.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User registerUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    /**
     * @param loginDto
     * @return Connected User
     * @throws Exception
     */
    public User loginUser(LoginDto loginDto) throws Exception {
        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new Exception("User not found"));

        if (bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return user;
        }
        else {
            throw new Exception("Invalid login and/or password");
        }
    }


    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }
}
