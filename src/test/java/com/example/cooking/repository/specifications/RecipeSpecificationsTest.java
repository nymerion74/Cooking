package com.example.cooking.repository.specifications;

import com.example.cooking.entity.Recipe;
import com.example.cooking.entity.User;
import com.example.cooking.repository.RecipeRepository;
import com.example.cooking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class RecipeSpecificationsTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User author = new User();
        author.setUsername("Chef John");
        author.setPassword("");
        userRepository.save(author);

        Recipe recipe = new Recipe();
        recipe.setName("Cheesecake");
        recipe.setIngredients("Cheese, Sugar, Eggs, Flour");
        recipe.setAuthor(author);
        recipeRepository.save(recipe);
    }

    @Test
    public void testHasIngredient() {
        Specification<Recipe> spec = RecipeSpecifications.hasIngredient("cheese");
        List<Recipe> results = recipeRepository.findAll(spec);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getName()).isEqualTo("Cheesecake");
    }

    @Test
    public void testHasName() {
        Specification<Recipe> spec = RecipeSpecifications.hasName("cheesecake");
        List<Recipe> results = recipeRepository.findAll(spec);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getIngredients()).contains("Cheese");
    }

    @Test
    public void testHasAuthor() {
        Specification<Recipe> spec = RecipeSpecifications.hasAuthor("chef john");
        List<Recipe> results = recipeRepository.findAll(spec);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getAuthor().getUsername()).isEqualToIgnoringCase("Chef John");
    }
}
