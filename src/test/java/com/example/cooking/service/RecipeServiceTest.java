package com.example.cooking.service;

import com.example.cooking.DTO.entry.CreateRecipeDto;
import com.example.cooking.entity.Recipe;
import com.example.cooking.entity.User;
import com.example.cooking.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private RecipeService recipeService;

    private Recipe recipe;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Spaghetti Carbonara");
        recipe.setIngredients("Spaghetti, Eggs, Cheese");
        recipe.setAuthor(user);
    }

    @Test
    void getAllRecipesShouldReturnListOfRecipes() {
        given(recipeRepository.findAll()).willReturn(Collections.singletonList(recipe));

        List<Recipe> result = recipeService.getAllRecipes();

        assertThat(result).containsExactly(recipe);
    }

    @Test
    void getRecipeByIdShouldReturnRecipe() {
        given(recipeRepository.findById(any(Long.class))).willReturn(Optional.of(recipe));

        Recipe result = recipeService.getRecipeById(1L);

        assertThat(result).isEqualTo(recipe);
    }

    @Test
    void createRecipeShouldSaveAndReturnRecipe() {
        given(recipeRepository.save(any(Recipe.class))).willReturn(recipe);

        Recipe result = recipeService.createRecipe(recipe);

        assertThat(result).isEqualTo(recipe);
    }

    @Test
    void updateRecipeShouldUpdateAndReturnRecipe() {
        given(recipeRepository.findById(any(Long.class))).willReturn(Optional.of(recipe));
        given(userService.findByUsername(any(String.class))).willReturn(user);
        given(recipeRepository.save(any(Recipe.class))).willReturn(recipe);

        CreateRecipeDto updatedRecipeDto = new CreateRecipeDto();
        updatedRecipeDto.setName("Updated Name");
        updatedRecipeDto.setIngredients("Updated Ingredients");
        updatedRecipeDto.setUsername("testUser");

        Recipe result = recipeService.updateRecipe(1L, updatedRecipeDto);

        assertThat(result.getName()).isEqualTo(updatedRecipeDto.getName());
        assertThat(result.getIngredients()).isEqualTo(updatedRecipeDto.getIngredients());
        assertThat(result.getAuthor()).isEqualTo(user);
    }

    @Test
    void deleteRecipeShouldUseRepository() {
        recipeService.deleteRecipe(1L);

        verify(recipeRepository).deleteById(1L);
    }

    @Test
    void searchRecipesShouldReturnFilteredRecipes() {
        given(recipeRepository.findAll(any(Specification.class))).willReturn(Collections.singletonList(recipe));

        List<Recipe> result = recipeService.searchRecipes("Eggs", null, null);

        assertThat(result).containsExactly(recipe);
    }
}
