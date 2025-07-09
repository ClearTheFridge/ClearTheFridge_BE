package com.example.clearthefridge.domain.recipe.dto;

import com.example.clearthefridge.domain.recipe.entity.Recipe;
import com.example.clearthefridge.domain.user.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeRequestDto {
    //저장data 관련 dto
    @Positive(message = "유효한 레시피 ID를 입력하세요.")
    private Long recipeId;

    @NotBlank(message = "레시피 이름은 필수입니다.")
    private String recipeName;

    @NotBlank(message = "레시피 설명은 필수입니다.")
    private String recipeContext;

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    @NotEmpty(message = "재료 목록을 하나 이상 포함해야 합니다.")
    @Valid
    private List<IngredientDto> ingredients;


    public Recipe toEntity(User user) {
        Recipe.RecipeBuilder builder = Recipe.builder()
                .recipeName(this.recipeName)
                .recipeContext(this.recipeContext)
                .user(user);
        if (this.recipeId != null) {
            builder.recipeId(this.recipeId);
        }
        return builder.build();
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class IngredientDto {

        @NotNull(message = "재료 ID는 필수입니다.")
        private Long ingredientId;

        @NotBlank(message = "수량(quantity)은 필수입니다.")
        private String quantity;
    }
}
