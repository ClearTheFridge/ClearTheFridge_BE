package com.example.clearthefridge.domain.recipe.dto;

import com.example.clearthefridge.domain.recipe.entity.Recipe;
import com.example.clearthefridge.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRecipeRequest {

    private String recipeName;
    private String recipeContext;
    private Long userId;
    private List<IngredientDto> ingredients;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class IngredientDto {
        private Long ingredientId;
        private String quantity;
    }

    //흠,,,,
    public Recipe toEntity(User user){
        return Recipe.builder()
                .recipeName(this.recipeName)
                .recipeContext(this.recipeContext)
                .user(user)
                .build();
    }
}
