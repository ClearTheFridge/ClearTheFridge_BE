package com.example.clearthefridge.domain.recipe.service.impl;

import com.example.clearthefridge.domain.recipe.dto.RecipeRequestDto;
import com.example.clearthefridge.domain.recipe.dto.RecipeResponseDto.RecipeSummaryDto;
import com.example.clearthefridge.domain.recipe.dto.RecipeResponseDto.RecipeDetailDto;
import com.example.clearthefridge.domain.recipe.entity.Recipe;
import com.example.clearthefridge.domain.recipe.repository.FindRecipeRepository;
import com.example.clearthefridge.domain.recipe.repository.RecipeRepository;
import com.example.clearthefridge.domain.recipe.service.RecipeService;
import com.example.clearthefridge.domain.user.entity.User;
import com.example.clearthefridge.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepo;
    private final FindRecipeRepository findRecipeRepo;
    private final UserRepository userRepository;

    @Override
    public RecipeDetailDto saveRecipe(RecipeRequestDto requestDto) {
        //유저 유효성 검사
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // User&Recipe Entity 저장
        Recipe recipe = requestDto.toEntity(user);
        //DB 저장 로직 요청
        recipeRepo.save(recipe);
        //저장된 상세정보 반환 > 후에 void 혹은 recipeId만 반환할지 고민중
        return RecipeDetailDto.fromEntity(recipe);
    }

    @Override
    public RecipeDetailDto updateRecipe(RecipeRequestDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Recipe recipe = requestDto.toEntity(user);
        recipeRepo.save(recipe);

        return RecipeDetailDto.fromEntity(recipe);
    }
     public void deleteRecipe(Long recipeId) {
        //조건 검사 추가하기
        recipeRepo.deleteById(recipeId);

    }

    @Override
    public List<RecipeSummaryDto> searchByTitle(String title) {
        List<Recipe> recipes = findRecipeRepo.findByrecipeNameContaining(title);
        return RecipeSummaryDto.fromEntityList(recipes);

    }

    @Override
    public List<RecipeSummaryDto> searchByIngredient(String ingreName) {
        List<Recipe> recipes = findRecipeRepo.findByIngredientName(ingreName);
        return RecipeSummaryDto.fromEntityList(recipes);
    }

    @Override
    public List<RecipeSummaryDto> searchByUsername(String userName) {
        List<Recipe> recipes = findRecipeRepo.findByUser_userName(userName);
        return RecipeSummaryDto.fromEntityList(recipes);
    }
}
