package com.example.cooking.controller;

import com.example.cooking.DTO.entry.CreateRecipeDto;
import com.example.cooking.DTO.mapper.RecipeMapper;
import com.example.cooking.DTO.response.RecipeDto;
import com.example.cooking.entity.Recipe;
import com.example.cooking.entity.User;
import com.example.cooking.service.RecipeService;
import com.example.cooking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;
    private final RecipeMapper recipeMapper;

    public RecipeController(RecipeService recipeService, UserService userService, RecipeMapper recipeMapper) {
        this.recipeService = recipeService;
        this.userService = userService;
        this.recipeMapper = recipeMapper;
    }

    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        List<RecipeDto> dtos = new ArrayList<>();
        for (var recipe : recipes) {
            dtos.add(recipeMapper.toDto(recipe));
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        RecipeDto dto = recipeMapper.toDto(recipe);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody CreateRecipeDto createRecipeDto) {
        User author = userService.findByUsername(createRecipeDto.getUsername());

        Recipe recipe = recipeMapper.toEntity(createRecipeDto);
        recipe.setAuthor(author);

        Recipe createdRecipe = recipeService.createRecipe(recipe);
        RecipeDto createdRecipeDto = recipeMapper.toDto(createdRecipe);

        return new ResponseEntity<>(createdRecipeDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @RequestBody CreateRecipeDto updateRecipeDto) {
        Recipe updatedRecipe = recipeService.updateRecipe(id, updateRecipeDto);
        RecipeDto dto = recipeMapper.toDto(updatedRecipe);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeDto>> searchRecipes(
            @RequestParam(required = false) String ingredient,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String author) {

        List<Recipe> recipes = recipeService.searchRecipes(ingredient, name, author);
        List<RecipeDto> dtos = new ArrayList<>();
        for (var recipe : recipes) {
            dtos.add(recipeMapper.toDto(recipe));
        }
        return ResponseEntity.ok(dtos);
    }
}
