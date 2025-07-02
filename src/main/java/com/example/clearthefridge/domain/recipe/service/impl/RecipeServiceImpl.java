package com.example.clearthefridge.domain.recipe.service.impl;

import com.example.clearthefridge.domain.recipe.entity.Recipe;
import com.example.clearthefridge.domain.recipe.repository.FindRecipeRepository;
import com.example.clearthefridge.domain.recipe.repository.RecipeRepository;
import com.example.clearthefridge.domain.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepo;
    private final FindRecipeRepository findRecipeRepo;

    @Override
    public Long saveRecipe(Recipe recipe) {
        return recipeRepo.save(recipe).getRecipeId();
    }

    @Override
    public Recipe updateRecipe(Recipe recipe) {
        return recipeRepo.save(recipe);
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        recipeRepo.deleteById(recipeId);

    }

    @Override
    public List<Recipe> searchByTitle(String title) {
        return findRecipeRepo.findByrecipeNameContaining(title);
    }

    @Override
    public List<Recipe> searchByIngredient(String ingreName) {
        return findRecipeRepo.findByIngredientName(ingreName)
    }

    @Override
    public List<Recipe> searchByUsername(String userName) {
        return findRecipeRepo.findByIngredientName(userName);
    }
}
