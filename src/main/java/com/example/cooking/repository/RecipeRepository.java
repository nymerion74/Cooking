package com.example.cooking.repository;

import com.example.cooking.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
    List<Recipe> findByIngredientsContainingIgnoreCase(String ingredient);

    List<Recipe> findByNameContainingIgnoreCase(String name);

    List<Recipe> findByAuthorUsernameIgnoreCase(String username);
}
