package com.example.clearthefridge.domain.recipe.refrige.service;


import com.example.clearthefridge.domain.recipe.refrige.dto.AddRequestDto;
import com.example.clearthefridge.domain.recipe.refrige.dto.GetResponseDto;

public interface UserIngredientService {

    void addIngredient(AddRequestDto ingredient);

    GetResponseDto getUserIngredients(Long userId);

}
