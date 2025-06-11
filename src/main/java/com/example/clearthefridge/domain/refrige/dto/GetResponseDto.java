package com.example.clearthefridge.domain.refrige.dto;

import com.example.clearthefridge.domain.refrige.entity.UserIngredient;
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
public class GetResponseDto {

    private Long userId;
    private List<UserIngredientDto> ingredientList;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserIngredientDto {
        private Long id;
        private String name;
        private String amount;
        private String unit;
        private LocalDateTime createdAt;
        private LocalDateTime expiryDate;

        public static UserIngredientDto from(UserIngredient userIngredient) {
            return UserIngredientDto.builder()
                    .id(userIngredient.getId())
                    .name(userIngredient.getIngredient().getName())
                    .amount(userIngredient.getAmount())
                    .unit(userIngredient.getUnit())
                    .createdAt(userIngredient.getCreatedAt())
                    .expiryDate(userIngredient.getExpiryDate())
                    .build();
        }
    }
}
