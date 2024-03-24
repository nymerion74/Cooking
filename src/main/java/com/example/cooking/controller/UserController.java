package com.example.cooking.controller;

import com.example.cooking.DTO.entry.LoginDto;
import com.example.cooking.DTO.mapper.UserMapper;
import com.example.cooking.DTO.response.UserDto;
import com.example.cooking.config.JwtTokenProvider;
import com.example.cooking.entity.User;
import com.example.cooking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, UserMapper userMapper, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody LoginDto user) {
        User entity = userMapper.toEntity(user);
        User registeredUser = userService.registerUser(entity);
        UserDto dto = userMapper.toDto(registeredUser);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) throws Exception {
        User loggedInUser = userService.loginUser(loginDto);
        UserDto dto = userMapper.toDto(loggedInUser);
        if (loggedInUser != null) {
            String token = jwtTokenProvider.createToken(loggedInUser.getUsername());
            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body(dto);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDto dto = userMapper.toDto(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
