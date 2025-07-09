package com.example.clearthefridge.domain.recipe.controller;

import com.example.clearthefridge.domain.recipe.dto.RecipeRequestDto;
import com.example.clearthefridge.domain.recipe.dto.RecipeResponseDto.RecipeDetailDto;
import com.example.clearthefridge.domain.recipe.dto.RecipeResponseDto.RecipeSummaryDto;
import com.example.clearthefridge.domain.recipe.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    //레시피 등록
    @PostMapping
    public ResponseEntity<RecipeDetailDto> saveRecipe(@RequestBody @Valid RecipeRequestDto recipeRequestDto) {
        RecipeDetailDto result = recipeService.saveRecipe(recipeRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }
    //레시피 수정
    @PutMapping("/{id}")
    public ResponseEntity<RecipeDetailDto> updateRecipe(
            @PathVariable Long id,
            @RequestBody RecipeRequestDto recipeRequestDto) {
        // DTO에 id 필드가 없다면, setter를 추가하거나 별도로 처리해주세요.
//        dto.setRecipeId(id);
        RecipeDetailDto updated = recipeService.updateRecipe(recipeRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(updated);
    }
    //레시피 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
    //제목으로 검색
    @GetMapping("/search/title")
    public ResponseEntity<List<RecipeSummaryDto>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(recipeService.searchByTitle(title));
    }
    //재료1개로 검색
    @GetMapping("/search/ingredient")
    public ResponseEntity<List<RecipeSummaryDto>> searchByIngredient(@RequestParam String ingredientName) {
        return ResponseEntity.ok(recipeService.searchByIngredient(ingredientName));
    }
    //레시피 등록유저 이름으로 검색
    @GetMapping("/search/user")
    public ResponseEntity<List<RecipeSummaryDto>> searchByUser(@RequestParam String username) {
        return ResponseEntity.ok(recipeService.searchByUsername(username));
    }
}