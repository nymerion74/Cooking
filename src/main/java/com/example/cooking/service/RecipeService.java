package com.example.cooking.service;

import com.example.cooking.DTO.entry.CreateRecipeDto;
import com.example.cooking.entity.Recipe;
import com.example.cooking.entity.User;
import com.example.cooking.repository.RecipeRepository;
import com.example.cooking.repository.specifications.RecipeSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserService userService;

    public RecipeService(RecipeRepository recipeRepository, UserService userService) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
    }

    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Long id, CreateRecipeDto updatedRecipe) {
        Recipe existingRecipe = getRecipeById(id);
        if (updatedRecipe.getName() != null) {
            existingRecipe.setName(updatedRecipe.getName());
        }
        if (updatedRecipe.getIngredients() != null) {
            existingRecipe.setIngredients(updatedRecipe.getIngredients());
        }
        if (updatedRecipe.getUsername() != null) {
            User newUser = userService.findByUsername(updatedRecipe.getUsername());
            existingRecipe.setAuthor(newUser);
        }
        return recipeRepository.save(existingRecipe);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    /**
     * this method take 3 criterias, they act as 'like' in SQL
     *
     * @param ingredient
     * @param name
     * @param author
     * @return a list of recipe matching criterias
     */
    public List<Recipe> searchRecipes(String ingredient, String name, String author) {
        // If no criteria, findAll
        if (ingredient == null && name == null && author == null) {
            return recipeRepository.findAll();
        }
        Specification<Recipe> spec = Specification.where(RecipeSpecifications.hasIngredient(ingredient))
                .and(RecipeSpecifications.hasName(name))
                .and(RecipeSpecifications.hasAuthor(author));

        return recipeRepository.findAll(spec);
    }

}
