package com.example.cooking.repository.specifications;

import com.example.cooking.entity.Recipe;
import org.springframework.data.jpa.domain.Specification;

public class RecipeSpecifications {

    public static Specification<Recipe> hasIngredient(String ingredient) {
        return (root, query, cb) -> {
            if (ingredient == null) {
                return null;
            }
            return cb.like(cb.lower(root.get("ingredients")), "%" + ingredient.toLowerCase() + "%");
        };
    }

    public static Specification<Recipe> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null) {
                return null;
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Recipe> hasAuthor(String authorUsername) {
        return (root, query, cb) -> {
            if (authorUsername == null) {
                return null;
            }
            return cb.like(cb.lower(root.join("author").get("username")), "%" + authorUsername.toLowerCase() + "%");
        };
    }
}
