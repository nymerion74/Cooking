package com.example.cooking.DTO.response;

public class RecipeDto {
    Long id;
    String name;
    String ingredients;
    String author;

    public RecipeDto(Long id, String name, String ingredients, String author) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.author = author;
    }

    public RecipeDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
