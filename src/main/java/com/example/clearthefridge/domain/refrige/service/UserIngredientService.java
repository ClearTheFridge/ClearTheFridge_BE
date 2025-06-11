package com.example.clearthefridge.domain.refrige.service;


import com.example.clearthefridge.domain.refrige.dto.AddRequestDto;
import com.example.clearthefridge.domain.refrige.dto.GetResponseDto;

public interface UserIngredientService {

    void addIngredient(AddRequestDto ingredient);

    GetResponseDto getUserIngredients(Long userId);

}
