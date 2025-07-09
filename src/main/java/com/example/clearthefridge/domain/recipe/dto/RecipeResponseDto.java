package com.example.clearthefridge.domain.recipe.dto;

import com.example.clearthefridge.domain.recipe.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


public class RecipeResponseDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class RecipeSummaryDto {
        private Long recipeId;
        private String recipeName;


        public static RecipeSummaryDto fromEntity(Recipe recipe) {
            return RecipeSummaryDto.builder()
                    .recipeId(recipe.getRecipeId())
                    .recipeName(recipe.getRecipeName())
                    .build();
        }

        // List<Recipe> → List<RecipeSummaryDto> 매핑
        public static List<RecipeSummaryDto> fromEntityList(List<Recipe> recipes) {
            return recipes.stream()
                    .map(RecipeSummaryDto::fromEntity)
                    .collect(Collectors.toList());
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class RecipeDetailDto{
        private Long recipeId;
        private String recipeName;
        private String recipeContext;
        private Long id; //userId
        private String userName;
        private List<IngredientDto> ingredients;
        private List<ReviewDto> reviews;


        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class IngredientDto {
            private Long ingredientId;
            private String ingredientName;
            private String amount;
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        private static class ReviewDto {
            private Long reviewId;
            private int rating;
            private String comment;
            private Long userId;
            private String userName;
        }
        public static RecipeDetailDto fromEntity(Recipe recipe) {
            List<IngredientDto> ings = recipe.getIngredients().stream()
                    .map(ri -> IngredientDto.builder()
                            .ingredientId(ri.getIngredient().getId())
                            .ingredientName(ri.getIngredient().getName())
                            .amount(ri.getAmount())
                            .build())
                    .collect(Collectors.toList());

            List<ReviewDto> revs = recipe.getReviews().stream()
                    .map(r -> ReviewDto.builder()
                            .reviewId(r.getId())
                            //댓글 개수는 제외
                            .comment(r.getContent())
                            .userId(r.getUser().getUserId())
                            .userName(r.getUser().getUserName())
                            .build())

                    .collect(Collectors.toList());

            return RecipeDetailDto.builder()
                    .recipeId(recipe.getRecipeId())
                    .recipeName(recipe.getRecipeName())
                    .recipeContext(recipe.getRecipeContext())
                    .userName(recipe.getUser().getUserName())
                    .ingredients(ings)
                    // 여기서 리스트 크기를 계산해서 채워줍니다.
                    .reviews(revs)
                    .build();
        }
    }
}



