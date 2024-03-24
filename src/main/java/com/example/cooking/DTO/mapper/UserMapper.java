package com.example.cooking.DTO.mapper;

import com.example.cooking.DTO.entry.LoginDto;
import com.example.cooking.DTO.response.UserDto;
import com.example.cooking.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(LoginDto dto);

    UserDto toDto(User user);
}
