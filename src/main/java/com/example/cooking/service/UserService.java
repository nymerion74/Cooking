package com.example.cooking.service;

import com.example.cooking.DTO.LoginDto;
import com.example.cooking.entity.User;
import com.example.cooking.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    /**
     * @param loginDto
     * @return Connected User
     * @throws Exception
     */
    public User loginUser(LoginDto loginDto) throws Exception {
        return userRepository.findUserByUsernameAndPassword(loginDto.getName(), loginDto.getPassword())
                .orElseThrow(() -> new Exception("User not found"));
    }


    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

}
