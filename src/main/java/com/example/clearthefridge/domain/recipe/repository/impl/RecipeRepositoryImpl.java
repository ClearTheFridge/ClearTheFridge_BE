package com.example.clearthefridge.domain.recipe.repository.impl;

import com.example.clearthefridge.domain.recipe.entity.Recipe;
import com.example.clearthefridge.domain.recipe.repository.RecipeRepository;

public class RecipeRepositoryImpl implements RecipeRepository {
    @Override
    public Recipe save(Recipe recipe) {
        return recipe;
    }

    @Override
    public Recipe update(Recipe recipe) {
        return null;
    }

    @Override
    public void delete(Recipe recipe) {

    }
}
