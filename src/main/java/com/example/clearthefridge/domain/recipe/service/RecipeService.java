package com.example.clearthefridge.domain.recipe.service;

import com.example.clearthefridge.domain.recipe.dto.RecipeRequestDto;
import com.example.clearthefridge.domain.recipe.dto.RecipeResponseDto.RecipeDetailDto;
import com.example.clearthefridge.domain.recipe.dto.RecipeResponseDto.RecipeSummaryDto;

import java.util.List;

public interface RecipeService  {

    RecipeDetailDto saveRecipe(RecipeRequestDto requestDto);
    RecipeDetailDto updateRecipe(RecipeRequestDto requestDto);
    void deleteRecipe(Long recipeId);

    List<RecipeSummaryDto> searchByTitle(String title);
    List<RecipeSummaryDto> searchByIngredient(String name);
    List<RecipeSummaryDto> searchByUsername(String username);

}
