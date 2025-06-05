package com.example.clearthefridge.controller;

import com.example.clearthefridge.dto.Ingredient.AddRequestDto;
import com.example.clearthefridge.service.UserIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/ingredient")
public class UserIngredientController {

    private final UserIngredientService userIngredientService;

    //냉장고 속 재료 추가
    @PostMapping("")
    public ResponseEntity<String> addIngredient(@RequestBody AddRequestDto ingredients) {
        userIngredientService.addIngredient(ingredients);
        return ResponseEntity.ok("재료 등록 성공");
    }





}
