package com.example.clearthefridge.service.impl;

import com.example.clearthefridge.domain.ingredient.entity.Ingredient;
import com.example.clearthefridge.domain.refrige.dto.ConsumeRequestDto;
import com.example.clearthefridge.domain.user.entity.User;
import com.example.clearthefridge.domain.refrige.dto.AddRequestDto;
import com.example.clearthefridge.domain.refrige.dto.GetResponseDto;
import com.example.clearthefridge.domain.refrige.entity.UserIngredient;
import com.example.clearthefridge.global.exception.CustomException;
import com.example.clearthefridge.global.exception.ErrorCode;
import com.example.clearthefridge.domain.ingredient.repository.IngredientRepository;
import com.example.clearthefridge.domain.refrige.repository.UserIngredientRepository;
import com.example.clearthefridge.domain.user.repository.UserRepository;
import com.example.clearthefridge.domain.refrige.service.UserIngredientService;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
                .userName("testUser")
                .build());
    }

    @Test
    @DisplayName("재료_추가_사용자가_없는_경우")
    void addIngredient_userNotFound_shouldThrow() {
        userRepository.deleteAll();

        AddRequestDto.IngredientDto dto = AddRequestDto.IngredientDto.builder()
                .name("토마토")
                .amount(2)
                .unit("개")
                .build();
        AddRequestDto request = AddRequestDto.builder()
                .userId(savedUser.getUserId())
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
                .amount(2)
                .unit("개")
                .build();
        AddRequestDto request = AddRequestDto.builder()
                .userId(savedUser.getUserId())
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
        assertThat(ui.getUser().getUserId()).isEqualTo(savedUser.getUserId());
        assertThat(ui.getIngredient().getId()).isEqualTo(ing.getId());
        assertThat(ui.getAmount()).isEqualTo(2);
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
                .amount(3)
                .unit("개")
                .build();
        AddRequestDto.IngredientDto dto2 = AddRequestDto.IngredientDto.builder()
                .name("당근")
                .amount(1)
                .unit("개")
                .build();
        AddRequestDto request = AddRequestDto.builder()
                .userId(savedUser.getUserId())
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
                .containsExactlyInAnyOrder(3, 1);
        assertThat(userIngredients)
                .extracting(UserIngredient::getUnit)
                .containsExactlyInAnyOrder("개", "개");
    }

    @Test
    @DisplayName("존재하지 않는 사용자 조회 시 예외 발생")
    void getUserIngredients_nonExistentUser_throwsException() {
        Long NotFoundUserId= savedUser.getUserId() + 1;

        assertThatThrownBy(() -> userIngredientService.getUserIngredients(NotFoundUserId))
                .isInstanceOf(CustomException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.NOT_FOUNT_USER);
    }

    @Test
    @DisplayName("사용자가 재료를 하나도 등록하지 않은 경우 빈 리스트 반환")
    void getUserIngredients_noIngredients_returnsEmptyList() {
        GetResponseDto response = userIngredientService.getUserIngredients(savedUser.getUserId());

        assertThat(response.getUserId()).isEqualTo(savedUser.getUserId());
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
                .amount(2)
                .unit("개")
                .createdAt(now.minusDays(1))
                .expiryDate(now.plusDays(4))
                .build());

        GetResponseDto response = userIngredientService.getUserIngredients(savedUser.getUserId());

        assertThat(response.getUserId()).isEqualTo(savedUser.getUserId());
        List<GetResponseDto.UserIngredientDto> list = response.getIngredientList();
        assertThat(list).hasSize(1);

        GetResponseDto.UserIngredientDto dto = list.get(0);
        assertThat(dto.getName()).isEqualTo("사과");
        assertThat(dto.getAmount()).isEqualTo(2);
        assertThat(dto.getUnit()).isEqualTo("개");
        assertThat(dto.getCreatedAt()).isNotNull();
        assertThat(dto.getExpiryDate()).isNotNull();
    }

    @Test
    @DisplayName("재료 차감 성공 테스트")
    void consumeIngredient_success() {
        // given
        User user = userRepository.save(User.builder().username("tester").build());

        Ingredient ingredient1 = ingredientRepository.save(Ingredient.builder().name("당근").build());
        Ingredient ingredient2 = ingredientRepository.save(Ingredient.builder().name("양파").build());

        UserIngredient userIng1 = userIngredientRepository.save(UserIngredient.builder()
                .user(user)
                .ingredient(ingredient1)
                .amount(100)
                .unit("g")
                .expiryDate(LocalDateTime.now().plusDays(3))
                .build());

        UserIngredient userIng2 = userIngredientRepository.save(UserIngredient.builder()
                .user(user)
                .ingredient(ingredient2)
                .amount(50)
                .unit("g")
                .expiryDate(LocalDateTime.now().plusDays(3))
                .build());

        List<ConsumeRequestDto> requestList = List.of(
                new ConsumeRequestDto(ingredient1.getId(), 40),
                new ConsumeRequestDto(ingredient2.getId(), 30)
        );

        // when
        userIngredientService.consumeIngredient(user.getId(), requestList);

        // then
        UserIngredient updated1 = userIngredientRepository.findById(userIng1.getId()).orElseThrow();
        UserIngredient updated2 = userIngredientRepository.findById(userIng2.getId()).orElseThrow();

        assertEquals(60, updated1.getAmount());
        assertEquals(20, updated2.getAmount());
    }


    @Test
    @DisplayName("재료 차감 실패 (수량 부족) 테스트")
    void consumeIngredient_fail() {
        // given
        User user = userRepository.save(User.builder().username("tester").build());
        Ingredient ingredient = ingredientRepository.save(Ingredient.builder().name("감자").build());

        UserIngredient userIngredient = userIngredientRepository.save(UserIngredient.builder()
                .user(user)
                .ingredient(ingredient)
                .amount(10) // 수량 적게 설정
                .unit("g")
                .expiryDate(LocalDateTime.now().plusDays(2))
                .build());

        List<ConsumeRequestDto> request = List.of(
                new ConsumeRequestDto(ingredient.getId(), 20)
        );

        // when & then
        assertThrows(CustomException.class, () -> {
            userIngredientService.consumeIngredient(user.getId(), request);
        });
    }
}