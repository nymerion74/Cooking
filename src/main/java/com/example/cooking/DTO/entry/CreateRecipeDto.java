package com.example.cooking.DTO.entry;

public class CreateRecipeDto {
    private String name;
    private String ingredients;
    private String username;

    public CreateRecipeDto(String name, String ingredients, String username) {
        this.name = name;
        this.ingredients = ingredients;
        this.username = username;
    }

    public CreateRecipeDto() {

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}