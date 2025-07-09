package com.example.clearthefridge.domain.recipe.refrige.service.impl;


import com.example.clearthefridge.domain.recipe.refrige.dto.AddRequestDto;
import com.example.clearthefridge.domain.recipe.refrige.dto.GetResponseDto;
import com.example.clearthefridge.domain.recipe.refrige.entity.UserIngredient;
import com.example.clearthefridge.domain.recipe.refrige.service.UserIngredientService;
import com.example.clearthefridge.domain.user.entity.User;
import com.example.clearthefridge.global.exception.CustomException;
import com.example.clearthefridge.global.exception.ErrorCode;
import com.example.clearthefridge.domain.ingredient.repository.IngredientRepository;
import com.example.clearthefridge.domain.user.repository.UserRepository;
import com.example.clearthefridge.domain.ingredient.entity.Ingredient;
import com.example.clearthefridge.domain.recipe.refrige.repository.UserIngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
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

    @Override
    @Transactional(readOnly = true)
    public GetResponseDto getUserIngredients(Long userId) {
        log.info("userId = " + userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT_USER));

        List<UserIngredient> userIngredients = userIngredientRepository.findByUser(user);

        List<GetResponseDto.UserIngredientDto> ingredientDtos = userIngredients.stream()
                .map(GetResponseDto.UserIngredientDto::from)
                .collect(Collectors.toList());

        return GetResponseDto.builder()
                .userId(userId)
                .ingredientList(ingredientDtos)
                .build();
    }
}
