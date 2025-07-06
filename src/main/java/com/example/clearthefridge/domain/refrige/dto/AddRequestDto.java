package com.example.clearthefridge.domain.refrige.dto;

import com.example.clearthefridge.domain.ingredient.entity.Ingredient;
import com.example.clearthefridge.domain.refrige.entity.UserIngredient;
import com.example.clearthefridge.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddRequestDto {

    private Long userId;

    @NotNull
    private List<IngredientDto> ingredientList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class IngredientDto {
        @NotBlank(message = "이름은 필수 항목 입니다.")
        private String name;

        private int amount;

        private String unit;

        public UserIngredient toEntity(User user, Ingredient ingredient) {
            LocalDateTime now = LocalDateTime.now();
            Integer recommendedDays = ingredient.getLifeDays();
            LocalDateTime expiry = now.plusDays(recommendedDays);

            return UserIngredient.builder()
                    .user(user)
                    .ingredient(ingredient)
                    .amount(this.amount)
                    .unit(this.unit)
                    .createdAt(now)
                    .expiryDate(expiry)
                    .build();
        }
    }
}
