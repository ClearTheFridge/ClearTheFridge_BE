package com.example.clearthefridge.domain.recipe.service;

import com.example.clearthefridge.domain.recipe.entity.Recipe;

import java.util.List;

public interface RecipeService  {

    Long saveRecipe(Recipe recipe);
    Recipe updateRecipe(Recipe recipe);
    void deleteRecipe(Long id);
    List<Recipe> searchByTitle(String title);
    List<Recipe> searchByIngredient(String name);
    List<Recipe> searchByUsername(String username);

}
