package com.example.clearthefridge.domain.refrige.controller;


import com.example.clearthefridge.domain.refrige.dto.AddRequestDto;
import com.example.clearthefridge.domain.refrige.dto.GetResponseDto;
import com.example.clearthefridge.domain.refrige.service.UserIngredientService;
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

    //사용자의 냉장고 속 재료 조회
    @GetMapping("")
    public ResponseEntity<GetResponseDto> addIngredient(@RequestParam Long userId) {

        return ResponseEntity.ok(userIngredientService.getUserIngredients(userId));
    }
}
