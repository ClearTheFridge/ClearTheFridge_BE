package com.example.clearthefridge.domain.recipe.controller;

import com.example.clearthefridge.domain.recipe.entity.Recipe;
import com.example.clearthefridge.domain.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody Recipe recipe) {
        Long id = recipeService.saveRecipe(recipe);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> update(@PathVariable Long id, @RequestBody Recipe recipe) {
        recipe.setId(id);
        return ResponseEntity.ok(recipeService.updateRecipe(recipe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<Recipe>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(recipeService.searchByTitle(title));
    }

    @GetMapping("/search/ingredient")
    public ResponseEntity<List<Recipe>> searchByIngredient(@RequestParam String ingredientName) {
        return ResponseEntity.ok(recipeService.searchByIngredient(ingredientName));
    }

    @GetMapping("/search/user")
    public ResponseEntity<List<Recipe>> searchByUser(@RequestParam String username) {
        return ResponseEntity.ok(recipeService.searchByUsername(username));
    }
}