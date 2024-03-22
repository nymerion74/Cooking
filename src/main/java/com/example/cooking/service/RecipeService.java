package com.example.cooking.service;

import com.example.cooking.entity.Recipe;
import com.example.cooking.repository.RecipeRepository;
import com.example.cooking.repository.specifications.RecipeSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
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

    public Recipe updateRecipe(Long id, Recipe recipe) {
        Recipe existingRecipe = getRecipeById(id);
        existingRecipe.setName(recipe.getName());
        existingRecipe.setIngredients(recipe.getIngredients());
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
