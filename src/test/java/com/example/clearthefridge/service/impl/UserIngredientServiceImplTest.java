package com.example.clearthefridge.service.impl;

import com.example.clearthefridge.dto.Ingredient.AddRequestDto;
import com.example.clearthefridge.dto.Ingredient.AddRequestDto.IngredientDto;
import com.example.clearthefridge.entity.Ingredient;
import com.example.clearthefridge.entity.User;
import com.example.clearthefridge.entity.UserIngredient;
import com.example.clearthefridge.global.exception.CustomException;
import com.example.clearthefridge.global.exception.ErrorCode;
import com.example.clearthefridge.repository.IngredientRepository;
import com.example.clearthefridge.repository.UserIngredientRepository;
import com.example.clearthefridge.repository.UserRepository;
import com.example.clearthefridge.service.UserIngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserIngredientServiceImplTest {

    @Autowired
    private UserIngredientService userIngredientService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private UserIngredientRepository userIngredientRepository;

    private User savedUser;

    @BeforeEach
    void setup() {
        userIngredientRepository.deleteAll();
        ingredientRepository.deleteAll();
        userRepository.deleteAll();

        savedUser = User.builder()
                .username("testUser")
                .build();
        savedUser = userRepository.save(savedUser);
    }

    @Test
    void 재료_추가_사용자가_없는_경우() {
        userRepository.deleteAll();

        IngredientDto dto = IngredientDto.builder()
                .name("토마토")
                .amount("2")
                .unit("개")
                .build();
        AddRequestDto request = AddRequestDto.builder()
                .userId(savedUser.getId())
                .ingredientList(Collections.singletonList(dto))
                .build();

        assertThatThrownBy(() -> userIngredientService.addIngredient(request))
                .isInstanceOf(CustomException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.NOT_FOUNT_USER);

        assertThat(userIngredientRepository.count()).isZero();
    }

    @Test
    void 새로운_재료_추가_성공() {
        // 기존에 "토마토"가 재료 테이블에 없는 경우
        assertThat(ingredientRepository.findByName("토마토")).isEmpty();

        IngredientDto dto = IngredientDto.builder()
                .name("토마토")
                .amount("2")
                .unit("개")
                .build();
        AddRequestDto request = AddRequestDto.builder()
                .userId(savedUser.getId())
                .ingredientList(Collections.singletonList(dto))
                .build();

        userIngredientService.addIngredient(request);

        List<Ingredient> ingredients = ingredientRepository.findAll();
        assertThat(ingredients).hasSize(1);
        Ingredient ing = ingredients.get(0);
        assertThat(ing.getName()).isEqualTo("토마토");
        assertThat(ing.getLifeDays()).isEqualTo(7);

        List<UserIngredient> userIngredients = userIngredientRepository.findAll();
        assertThat(userIngredients).hasSize(1);
        UserIngredient ui = userIngredients.get(0);
        assertThat(ui.getUser().getId()).isEqualTo(savedUser.getId());
        assertThat(ui.getIngredient().getId()).isEqualTo(ing.getId());
        assertThat(ui.getAmount()).isEqualTo("2");
        assertThat(ui.getUnit()).isEqualTo("개");
        assertThat(ui.getCreatedAt()).isNotNull();
        assertThat(ui.getExpiryDate()).isNotNull();
    }

    @Test
    void 여러_재료_추가_성공() {
        Ingredient existing = Ingredient.builder()
                .name("양파")
                .lifeDays(10)
                .build();
        existing = ingredientRepository.save(existing);

        IngredientDto dto1 = IngredientDto.builder()
                .name("양파")
                .amount("3")
                .unit("개")
                .build();
        IngredientDto dto2 = IngredientDto.builder()
                .name("당근")
                .amount("1")
                .unit("개")
                .build();
        AddRequestDto request = AddRequestDto.builder()
                .userId(savedUser.getId())
                .ingredientList(List.of(dto1, dto2))
                .build();

        userIngredientService.addIngredient(request);

        List<Ingredient> ingredients = ingredientRepository.findAll();
        assertThat(ingredients).hasSize(2);

        List<UserIngredient> userIngredients = userIngredientRepository.findAll();
        assertThat(userIngredients).hasSize(2);
        assertThat(userIngredients)
                .extracting(ui -> ui.getIngredient().getName())
                .containsExactlyInAnyOrder("양파", "당근");
        assertThat(userIngredients)
                .extracting(UserIngredient::getAmount)
                .containsExactlyInAnyOrder("3", "1");
        assertThat(userIngredients)
                .extracting(UserIngredient::getUnit)
                .containsExactlyInAnyOrder("개", "개");
    }
}