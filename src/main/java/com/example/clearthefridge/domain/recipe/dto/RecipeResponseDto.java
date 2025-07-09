package com.example.clearthefridge.domain.recipe.dto;

import com.example.clearthefridge.domain.recipe.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RecipeResposeDto {
    private Long id;
    private String recipeName;
    private String username;

    public static RecipeResponseDto fromEntity(Recipe recipe) {
        return RecipeResponseDto.builder()
                .id(recipe.getId())
                .recipeName(recipe.getRecipeName())
                .username(recipe.getUser().getUsername())
                .build();
    }

}
