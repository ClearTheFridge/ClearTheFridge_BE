package com.example.clearthefridge.domain.refrige.service;


import com.example.clearthefridge.domain.refrige.dto.AddRequestDto;
import com.example.clearthefridge.domain.refrige.dto.ConsumeRequestDto;
import com.example.clearthefridge.domain.refrige.dto.GetResponseDto;

import java.util.List;

public interface UserIngredientService {

    void addIngredient(AddRequestDto ingredient);

    GetResponseDto getUserIngredients(Long userId);

    void consumeIngredient(Long userId, List<ConsumeRequestDto> ingredients);
}
