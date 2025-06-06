package com.example.clearthefridge.userIngredient.service;


import com.example.clearthefridge.userIngredient.dto.AddRequestDto;
import com.example.clearthefridge.userIngredient.dto.GetResponseDto;

public interface UserIngredientService {

    void addIngredient(AddRequestDto ingredient);

    GetResponseDto getUserIngredients(Long userId);

}
