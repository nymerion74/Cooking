package com.example.cooking.controller;

import com.example.cooking.DTO.entry.CreateRecipeDto;
import com.example.cooking.DTO.mapper.RecipeMapper;
import com.example.cooking.DTO.response.RecipeDto;
import com.example.cooking.entity.Recipe;
import com.example.cooking.entity.User;
import com.example.cooking.service.RecipeService;
import com.example.cooking.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class RecipeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RecipeService recipeService;

    @Mock
    private UserService userService;

    @Mock
    private RecipeMapper recipeMapper;

    @InjectMocks
    private RecipeController recipeController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void getAllRecipesShouldReturnRecipes() throws Exception {
        RecipeDto recipeDto = new RecipeDto(1L, "Test Recipe", "Test Ingredients", "testUser");
        List<RecipeDto> dtos = Arrays.asList(recipeDto);

        when(recipeService.getAllRecipes()).thenReturn(Arrays.asList(new Recipe()));
        when(recipeMapper.toDto(any())).thenReturn(recipeDto);

        mockMvc.perform(get("/recipes")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(recipeDto.getName()));
    }

    @Test
    void getRecipeByIdShouldReturnRecipe() throws Exception {
        RecipeDto recipeDto = new RecipeDto(1L, "Test Recipe", "Test Ingredients", "testUser");

        when(recipeService.getRecipeById(eq(1L))).thenReturn(new Recipe());
        when(recipeMapper.toDto(any())).thenReturn(recipeDto);

        mockMvc.perform(get("/recipes/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(recipeDto.getName()));
    }

    @Test
    void createRecipeShouldReturnCreatedRecipe() throws Exception {
        CreateRecipeDto createRecipeDto = new CreateRecipeDto("New Recipe", "Ingredients", "testUser");
        RecipeDto recipeDto = new RecipeDto(1L, "New Recipe", "Ingredients", "testUser");
        User user = new User();
        user.setUsername("testUser");
        Recipe t = new Recipe();
        t.setIngredients("Ingredients");
        t.setName("New Recipe");
        t.setAuthor(user);
        when(userService.findByUsername(eq("testUser"))).thenReturn(user);
        when(recipeService.createRecipe(any())).thenReturn(t);
        when(recipeMapper.toEntity(any())).thenReturn(t);
        when(recipeMapper.toDto(any())).thenReturn(recipeDto);

        mockMvc.perform(post("/recipes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createRecipeDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(recipeDto.getName()));
    }

    @Test
    void updateRecipeShouldReturnUpdatedRecipe() throws Exception {
        CreateRecipeDto updateRecipeDto = new CreateRecipeDto("Updated Recipe", "Updated Ingredients", "testUser");
        RecipeDto recipeDto = new RecipeDto(1L, "Updated Recipe", "Updated Ingredients", "testUser");

        when(recipeService.updateRecipe(eq(1L), any(CreateRecipeDto.class))).thenReturn(new Recipe());
        when(recipeMapper.toDto(any())).thenReturn(recipeDto);

        mockMvc.perform(put("/recipes/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateRecipeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updateRecipeDto.getName()));
    }

    @Test
    void deleteRecipeShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/recipes/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
