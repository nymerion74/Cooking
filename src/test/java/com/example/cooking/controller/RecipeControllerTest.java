package com.example.cooking.controller;

import com.example.cooking.entity.Recipe;
import com.example.cooking.entity.User;
import com.example.cooking.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RecipeController.class)
@ActiveProfiles("test")
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Test Recipe");
        recipe.setIngredients("Test Ingredients");
        User user = new User();
        user.setPassword("");
        user.setId(1l);
        user.setUsername("test");
        recipe.setAuthor(user);
    }

    @Test
    void getAllRecipesShouldReturnRecipes() throws Exception {
        List<Recipe> recipes = Arrays.asList(recipe);
        given(recipeService.getAllRecipes()).willReturn(recipes);

        mockMvc.perform(get("/recipes")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(recipe.getName())));
    }

    @Test
    void getRecipeByIdShouldReturnRecipe() throws Exception {
        given(recipeService.getRecipeById(recipe.getId())).willReturn(recipe);

        mockMvc.perform(get("/recipes/{id}", recipe.getId())
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(recipe.getName())));
    }

    @Test
    void createRecipeShouldReturnCreatedRecipe() throws Exception {
        given(recipeService.createRecipe(any())).willReturn(recipe);

        mockMvc.perform(post("/recipes")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(recipe.getName())));
    }

    @Test
    void updateRecipeShouldReturnUpdatedRecipe() throws Exception {
        given(recipeService.updateRecipe(eq(recipe.getId()), any(Recipe.class))).willReturn(recipe);

        mockMvc.perform(put("/recipes/{id}", recipe.getId())
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(recipe.getName())));
    }

    @Test
    void deleteRecipeShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/recipes/{id}", recipe.getId())
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
