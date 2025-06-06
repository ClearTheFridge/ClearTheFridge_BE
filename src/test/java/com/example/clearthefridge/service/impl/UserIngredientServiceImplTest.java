package com.example.clearthefridge.service.impl;

import com.example.clearthefridge.ingredient.entity.Ingredient;
import com.example.clearthefridge.user.entity.User;
import com.example.clearthefridge.userIngredient.dto.AddRequestDto;
import com.example.clearthefridge.userIngredient.dto.GetResponseDto;
import com.example.clearthefridge.userIngredient.entity.UserIngredient;
import com.example.clearthefridge.global.exception.CustomException;
import com.example.clearthefridge.global.exception.ErrorCode;
import com.example.clearthefridge.ingredient.repository.IngredientRepository;
import com.example.clearthefridge.userIngredient.repository.UserIngredientRepository;
import com.example.clearthefridge.user.UserRepository;
import com.example.clearthefridge.userIngredient.service.UserIngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

        savedUser = userRepository.save(User.builder()
                .username("testUser")
                .build());
    }

    @Test
    @DisplayName("재료_추가_사용자가_없는_경우")
    void addIngredient_userNotFound_shouldThrow() {
        userRepository.deleteAll();

        AddRequestDto.IngredientDto dto = AddRequestDto.IngredientDto.builder()
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
    @DisplayName("새로운_재료_추가_성공")
    void addIngredient_newIngredient_success() {
        // 기존에 "토마토"가 재료 테이블에 없는 경우
        assertThat(ingredientRepository.findByName("토마토")).isEmpty();

        AddRequestDto.IngredientDto dto = AddRequestDto.IngredientDto.builder()
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
    @DisplayName("여러_재료_추가_성공")
    void addIngredient_multipleIngredients_success() {
        Ingredient existing = Ingredient.builder()
                .name("양파")
                .lifeDays(10)
                .build();
        ingredientRepository.save(existing);

        AddRequestDto.IngredientDto dto1 = AddRequestDto.IngredientDto.builder()
                .name("양파")
                .amount("3")
                .unit("개")
                .build();
        AddRequestDto.IngredientDto dto2 = AddRequestDto.IngredientDto.builder()
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

    @Test
    @DisplayName("존재하지 않는 사용자 조회 시 예외 발생")
    void getUserIngredients_nonExistentUser_throwsException() {
        Long NotFoundUserId= savedUser.getId() + 1;

        assertThatThrownBy(() -> userIngredientService.getUserIngredients(NotFoundUserId))
                .isInstanceOf(CustomException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.NOT_FOUNT_USER);
    }

    @Test
    @DisplayName("사용자가 재료를 하나도 등록하지 않은 경우 빈 리스트 반환")
    void getUserIngredients_noIngredients_returnsEmptyList() {
        GetResponseDto response = userIngredientService.getUserIngredients(savedUser.getId());

        assertThat(response.getUserId()).isEqualTo(savedUser.getId());
        assertThat(response.getIngredientList()).isEmpty();
    }

    @Test
    @DisplayName("사용자의 재료 조회 시 올바른 리스트 반환")
    void getUserIngredients_success() {
        LocalDateTime now = LocalDateTime.now();

        Ingredient ingredient = ingredientRepository.save(Ingredient.builder()
                .name("사과")
                .lifeDays(5)
                .build());

        userIngredientRepository.save(UserIngredient.builder()
                .user(savedUser)
                .ingredient(ingredient)
                .amount("2")
                .unit("개")
                .createdAt(now.minusDays(1))
                .expiryDate(now.plusDays(4))
                .build());

        GetResponseDto response = userIngredientService.getUserIngredients(savedUser.getId());

        assertThat(response.getUserId()).isEqualTo(savedUser.getId());
        List<GetResponseDto.UserIngredientDto> list = response.getIngredientList();
        assertThat(list).hasSize(1);

        GetResponseDto.UserIngredientDto dto = list.get(0);
        assertThat(dto.getName()).isEqualTo("사과");
        assertThat(dto.getAmount()).isEqualTo("2");
        assertThat(dto.getUnit()).isEqualTo("개");
        assertThat(dto.getCreatedAt()).isNotNull();
        assertThat(dto.getExpiryDate()).isNotNull();
    }
}