package com.example.clearthefridge.service.impl;


import com.example.clearthefridge.dto.Ingredient.AddRequestDto;
import com.example.clearthefridge.dto.Ingredient.GetResponseDto;
import com.example.clearthefridge.entity.Ingredient;
import com.example.clearthefridge.entity.User;
import com.example.clearthefridge.entity.UserIngredient;
import com.example.clearthefridge.global.exception.CustomException;
import com.example.clearthefridge.global.exception.ErrorCode;
import com.example.clearthefridge.repository.IngredientRepository;
import com.example.clearthefridge.repository.UserIngredientRepository;
import com.example.clearthefridge.repository.UserRepository;
import com.example.clearthefridge.service.UserIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserIngredientServiceImpl implements UserIngredientService {

    private final UserIngredientRepository userIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addIngredient(AddRequestDto ingredients) {
        User user = userRepository.findById(ingredients.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT_USER));

        for (AddRequestDto.IngredientDto dto : ingredients.getIngredientList()) {
            Ingredient ingredient = ingredientRepository.findByName(dto.getName())
                    .orElseGet(() -> {   //이전에 등록 되어 있지 않은 재료들 저장
                        Ingredient newIngredient = Ingredient.builder()
                                .name(dto.getName())
                                .lifeDays(7)
                                .build();
                        return ingredientRepository.save(newIngredient);
                    });
            UserIngredient userIngredient = dto.toEntity(user, ingredient);
            userIngredientRepository.save(userIngredient);
        }
    }
}
