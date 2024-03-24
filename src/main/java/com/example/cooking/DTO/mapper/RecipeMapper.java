package com.example.cooking.DTO.mapper;

import com.example.cooking.DTO.entry.CreateRecipeDto;
import com.example.cooking.DTO.response.RecipeDto;
import com.example.cooking.entity.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    @Mapping(target = "author", source = "author.username")
    RecipeDto toDto(Recipe recipe);

    @Mapping(target = "author", ignore = true)
    Recipe toEntity(CreateRecipeDto createRecipeDto);
}