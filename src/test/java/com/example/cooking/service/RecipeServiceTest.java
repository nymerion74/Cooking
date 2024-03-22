package com.example.cooking.service;

import com.example.cooking.entity.Recipe;
import com.example.cooking.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Test Recipe");
        recipe.setIngredients("Ingredient1, Ingredient2");
    }

    @Test
    void getAllRecipesShouldReturnListOfRecipes() {
        when(recipeRepository.findAll()).thenReturn(Collections.singletonList(recipe));

        List<Recipe> recipes = recipeService.getAllRecipes();

        assertThat(recipes).hasSize(1);
        assertThat(recipes.get(0)).isEqualTo(recipe);
    }

    @Test
    void getRecipeByIdShouldReturnRecipe() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Recipe foundRecipe = recipeService.getRecipeById(1L);

        assertThat(foundRecipe).isEqualTo(recipe);
    }

    @Test
    void getRecipeByIdShouldThrowExceptionWhenNotFound() {
        long recipeId = 2L;
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recipeService.getRecipeById(recipeId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Recipe not found with id: " + recipeId);
    }

    @Test
    void createRecipeShouldSaveAndReturnRecipe() {
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        Recipe savedRecipe = recipeService.createRecipe(recipe);

        assertThat(savedRecipe).isEqualTo(recipe);
        verify(recipeRepository).save(recipe);
    }

    @Test
    void updateRecipeShouldChangeDetailsAndReturnUpdatedRecipe() {
        Recipe updatedRecipe = new Recipe();
        updatedRecipe.setId(1L);
        updatedRecipe.setName("Updated Recipe");
        updatedRecipe.setIngredients("UpdatedIngredient1, UpdatedIngredient2");

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(updatedRecipe);

        Recipe result = recipeService.updateRecipe(1L, updatedRecipe);

        assertThat(result.getName()).isEqualTo(updatedRecipe.getName());
        assertThat(result.getIngredients()).isEqualTo(updatedRecipe.getIngredients());
    }

    @Test
    void searchRecipesWithMultipleCriteriaShouldReturnFilteredRecipes() {
        // Given
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.setName("Chocolate Cake");
        recipe1.setIngredients("Chocolate, Flour, Sugar");

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setName("Apple Pie");
        recipe2.setIngredients("Apple, Sugar, Flour");

        List<Recipe> allRecipes = Arrays.asList(recipe1, recipe2);

        when(recipeRepository.findAll(any(Specification.class))).thenReturn(allRecipes);

        // When
        String ingredient = "Sugar";
        String name = null; // Simulating a search by ingredient only
        String author = null;
        List<Recipe> result = recipeService.searchRecipes(ingredient, name, author);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(recipe1, recipe2);

        verify(recipeRepository).findAll(any(Specification.class));
    }

}
