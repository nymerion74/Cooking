package com.example.cooking.repository;

import com.example.cooking.entity.Recipe;
import com.example.cooking.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        User author = new User();
        author.setUsername("authorName");
        author.setPassword("password");
        userRepository.save(author);

        // Setup a recipe
        recipe = new Recipe();
        recipe.setName("Chocolate Cake");
        recipe.setIngredients("Chocolate, Flour, Sugar, Eggs");
        recipe.setAuthor(author); // Set the author of the recipe
        recipeRepository.save(recipe);
    }

    @Test
    public void findByIngredientsContainingIgnoreCaseShouldReturnRecipes() {
        List<Recipe> recipes = recipeRepository.findByIngredientsContainingIgnoreCase("chocolate");

        assertThat(recipes).hasSize(1);
        assertThat(recipes.get(0).getName()).isEqualTo("Chocolate Cake");
    }

    @Test
    public void findByNameContainingIgnoreCaseShouldReturnRecipes() {
        List<Recipe> recipes = recipeRepository.findByNameContainingIgnoreCase("cake");

        assertThat(recipes).hasSize(1);
        assertThat(recipes.get(0).getIngredients()).contains("Chocolate");
    }

    @Test
    public void findByAuthorUsernameIgnoreCaseShouldReturnRecipes() {
        List<Recipe> recipes = recipeRepository.findByAuthorUsernameIgnoreCase("authorname");

        assertThat(recipes).hasSize(1);
        assertThat(recipes.get(0).getAuthor().getUsername()).isEqualToIgnoringCase("authorName");
    }
}
